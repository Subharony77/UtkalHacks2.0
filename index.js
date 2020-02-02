'use strict';

const {
    dialogflow,
    Suggestions,
    Table,
} = require('actions-on-google');

const functions = require('firebase-functions');
const admin = require('firebase-admin');

const app = dialogflow({ debug: true });

admin.initializeApp(functions.config().firebase);
admin.firestore().settings({ timestampsInSnapshots: true });

const db = admin.firestore();

const prescriptionRef = db.collection('prescriptions');

app.intent('Default Welcome Intent', (conv) => {
    if(conv.user.storage.license) {
        conv.ask('Welcome back to Doc Talks. What can I do for you?');
        conv.ask(new Suggestions(['Dictate prescription']));
    }
    else {
        conv.ask('Welcome to Doc Talks. To ensure a smoother workflow and to avoid confusions I\'ll be needing you to input your license number.');
        conv.ask('Please enter your License Number.');
    }
});

app.intent('license', (conv, { license }) => {
    conv.user.storage.license = license;
    conv.ask('Thanks for that. You can proceed without any issues now.');
    conv.ask('How can I help you today?');
    conv.ask(new Suggestions(['Dictate prescription']));
});

app.intent('prescription', (conv) => {
    if(conv.user.storage.license) {
        conv.ask('Let\'s get started! Can I have the patient\'s name please?');
    }
    else {
        conv.ask('Sorry but you have not provided a license number yet. Without that I just cannot proceed further.');
        conv.close('Hope you understand. Until next time, cheers!');
    }
});

app.intent('name', (conv, { x }) => {
    conv.data.name = x;
    conv.ask(`Please enter the age of ${x}.`);
});

app.intent('age', (conv, { age }) => {
    conv.data.age = age;
    conv.ask(`I got that. What's the gender of ${conv.data.name}`);
    conv.ask(new Suggestions(['Male', 'Female']));
});

app.intent('gender', (conv, { gender }) => {
    conv.data.gender = gender;
    conv.ask(`What is the symptom ${conv.data.name} is experiencing?`);
    conv.ask('Here are some of the common symptoms from which you can choose.');
    conv.ask(new Suggestions(['Headache', 'Anxiety', 'Cough', 'Fatigue', 'Migraine']));
});

app.intent('symptoms', (conv, { symptoms }) => {
    conv.data.symptom = symptoms;
    conv.ask(`Okay! What is ${conv.data.name} diagnosed with?`);
    conv.ask(new Suggestions(['Maleria', 'Pnemonia', 'Insomnia', 'High Fever', 'Diabetes']));
});

app.intent('diagnosis', (conv, { diseases }) => {
    conv.data.disease = diseases;
    conv.ask(`What medicines do you prescribe for ${conv.data.name}?`);
    conv.ask(new Suggestions(['Paracetamol','Azithromycin']));
});

app.intent('medicines', (conv, { medicines }) => {
    conv.data.medicine = medicines;
    conv.ask(`Some extra beneficial advices for ${conv.data.name}?`);
    conv.ask(new Suggestions(['Drink more water','Sleep early','Reduce Stress','Proper bowel movements']));
});

app.intent('advices', (conv, { advices }) => {
    conv.data.advice = advices;
    conv.ask('Say Show me to get a preview of your prescription');
    conv.ask(new Suggestions(['Show me']));
});

app.intent('finalize', (conv) => {
    conv.ask('This is how the final prescription looks like :');
    conv.ask(new Table({
        dividers: true,
        columns: [{
            header: ' ',
            align: 'CENTER',
        },
        {
            header: ' ',
            align: 'CENTER',
        },],
        rows: [
            ['Name', `${conv.data.name}`],
            ['Age', `${conv.data.age}`],
            ['Gender', `${conv.data.gender}`],
            ['Symptom', `${conv.data.symptom}`],
            ['Diagnosis', `${conv.data.disease}`],
            ['Medicine', `${conv.data.medicine}`],
            ['Advice', `${conv.data.advice}`],
        ],
    }));
    conv.ask('Do you want me to save this?');
    conv.ask(new Suggestions(['Yes', 'No']));
});

app.intent('finalize - yes', (conv) => {
    var doc = conv.user.storage.license + Date.now();
    return prescriptionRef.doc(`${doc}`).set({
        name: conv.data.name,
        age: conv.data.age,
        gender: conv.data.gender,
        symptoms: conv.data.symptom,
        diagnosis: conv.data.disease,
        medicines: conv.data.medicine,
        advice: conv.data.advice,
    }).then(ref => {
        console.log(ref.id);
        conv.ask('Yohoo! I saved that. Want to save another prescription?');
    }).catch(err => {
        console.log(err);
        conv.close('Something went south.');
    });
});


exports.dialogflowFirebaseFulfillment = functions.https.onRequest(app);