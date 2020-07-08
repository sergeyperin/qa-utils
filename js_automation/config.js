/*
Basic configuration to run your cucumber
feature files and step definitions with protractor.
**/
const os = require('os');

var getPlatform = function () {
  switch(os.platform()) {
    case 'linux': return 'Linux';
    case 'win32': return 'Windows';
    case 'darwin': return 'OSX';
  }
};


var config = {
    //seleniumAddress: 'http://localhost:4444/wd/hub',
    baseUrl: 'https://some-qa.domain.com',
    capabilities: {
      browserName: 'chrome',
      chromeOptions: {
        //args: [ "--headless", "--disable-gpu", "--window-size=1024,800" ]
      },
      metadata: {
          browser: {
              name: 'chrome'//config.capabilities.browserName
          },
          device: getPlatform(),
          platform: {
              name: getPlatform(),
              version: os.release()
          }
        }
    },  
    framework: 'custom',  // set to "custom" instead of cucumber.
    frameworkPath: require.resolve('protractor-cucumber-framework'),  // path relative to the current config file
  
    specs: [
      './features/login/*.feature'     // Specs here are the cucumber feature files
    ],
  
    // cucumber command line options
    cucumberOpts: {
      require: ['./spec/**/*.js'],  // require step definition files before executing features
      tags: [],                      // <string[]> (expression) only execute the features or scenarios with tags matching the expression
      strict: true,                  // <boolean> fail if there are any undefined or pending steps
      format: ["json:results/results.json"],            // <string[]> (type[:path]) specify the output format, optionally supply PATH to redirect formatter output (repeatable)
      'dry-run': false,              // <boolean> invoke formatters without executing steps
      compiler: []                   // <string[]> ("extension:module") require files with the given EXTENSION after requiring MODULE (repeatable)
    },

    params: {
      login: {
        username: 'TA_1stAutoUser',
        password: '1stAutoUserP@$$1'
      },
      timeout: {
        PAGE_LOAD: 180*1000, 
        COMPLETE_ACTION: 5*1000
      }
    },
  
   onPrepare: function () {
      browser.waitForAngularEnabled(true);
      browser.manage().window().maximize(); // maximize the browser before executing the feature files
    },

    allScriptsTimeout: 5000,

    plugins: [{
      package: 'protractor-multiple-cucumber-html-reporter-plugin',
      options:{
        automaticallyGenerateReport: true,
        removeExistingJsonReportFile: true,
        customData: {
          title: 'Run info',
          data: [
              {label: 'Project', value: 'Conform'},
              {label: 'Release', value: '5.0.4'},
              {label: 'Cycle', value: '1'},
              {label: 'Execution Start Time', value: new Date()},
              {label: 'Execution End Time', value: new Date()}
          ]
        }
      }
    }]
  };

exports.config = config;
