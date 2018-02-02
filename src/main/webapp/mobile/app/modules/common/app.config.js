//set global configuration of application and it can be accessed by injecting appConstants in any modules

(function () {
    angular
        .module('cancure')
        .constant('appConfig', {

            title: "Cancure", // app name      
            version: "1.1.100", // app version 
            lang: "en", // app default locale format

            baseURL: 'http://cancure.in.net/', // app service URL 
            baseURL: 'http://localhost:8080/', // app service URL  
            requestURL: {
                login: 'oauth/token',
                whoami: 'user/whoami',
                myQueue: 'tasks/my',
                patientHistory: 'tasks/history/',
                hpocDoctors: 'doctor/hpoclist', // to get all doctors under that HPOC
                pushId: 'user/pushid/save' // to get all doctors under that HPOC
            },
            fileType: ['txt', 'doc', 'docx', 'pdf', 'png', 'jpg', 'jpeg', 'xlsx', 'xlsm'], // file types allowed for upload
            imgFileType: ['png', 'jpg', 'jpeg'], // image types allowed for upload

            rolesList: [] // stores all role names (authority) 

        });
})();