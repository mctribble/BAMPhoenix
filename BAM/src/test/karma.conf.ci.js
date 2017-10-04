//from a tutorial on https://blog.akquinet.de/2014/11/25/js-test-coverage/
var baseConfig = require('./karma.conf.js');

module.exports = function (config) {
    // Load base config
    baseConfig(config);

    // Override base config
    config.set({
        singleRun: true,
        colors:    false,
        autoWatch: false,
        reporters: ['progress', 'junit', 'coverage'],
        preprocessors:    {
            'src/main/webapp/static/**/**/*.js':   ['coverage']

        },
        browsers:  ['ChromeHeadless'],
        junitReporter: {
            outputFile: 'reports/junit/TESTS-xunit.xml'
        },
        coverageReporter: {
            type:   'lcov',
            dir:    'reports',
            subdir: 'coverage'
        }
    });
};