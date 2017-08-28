// Karma configuration
// Generated on Tue Aug 15 2017 13:40:17 GMT-0400 (Eastern Daylight Time)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
    	'node_modules/angular/angular.js',
        'https://code.jquery.com/jquery-1.11.2.min.js',
    	'node_modules/angular-mocks/angular-mocks.js',
    	'node_modules/angular-route/angular-route.js',
        'node_modules/angulartics/src/angulartics.js',
        'node_modules/angular-google-analytics/dist/angular-google-analytics.js',
    	'node_modules/angular-ui-bootstrap/dist/ui-bootstrap.js',
    	'node_modules/angular-drag-and-drop-lists/angular-drag-and-drop-lists.js',
    	'node_modules/angulartics-google-analytics/lib/angulartics-ga.js',
    	'../../src/main/webapp/static/js/app.js',
    	'../../src/main/webapp/static/js/service.js',
    	'../../src/main/webapp/static/controller/*.js',
    	'tests/js/*Test.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}
