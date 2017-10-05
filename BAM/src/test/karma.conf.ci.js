//from a tutorial on https://blog.akquinet.de/2014/11/25/js-test-coverage/
var baseConfig = require('./karma.conf.js');

module.exports = function (config) {
    // Load base config
    baseConfig(config);

    // Override base config
    config.set({
        singleRun: true,  //do everything in one go
        colors:    false, //turn off color coded output, as jenkins doesnt like it
        autoWatch: false, 
        reporters: ['progress', 'junit', 'coverage'],
        preprocessors:    {
            'src/main/webapp/static/js/**/*.js':   ['coverage'] //files that these tests are meant to cover
        },
        browsers:  ['ChromeHeadless'], //browser to use

        //configuration for reporting results.  WARNING: SonarQube looks for reports in TESTS-xunit.xml, so do not rename this!
        junitReporter: {
            outputFile: 'reports/junit/TESTS-xunit.xml' 
        },

        //configuration for reporting coverage
        coverageReporter: {
            type:   'lcov',
            dir:    'reports',
            subdir: 'coverage'
        }
    });
};