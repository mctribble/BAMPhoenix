//based on a tutorial at https://blog.akquinet.de/2014/11/25/js-test-coverage/
//this file runs the Jasmine unit tests and generates coverage reports

//dependencies
var gulp = require('gulp');
var karma = require('karma').server;
var replace = require('gulp-replace');

//the generated reports provide slightly wrong paths
//this function fixes them
var postprocessLCOV = function() {
    return gulp.src('reports/coverage/lcov.info')
        .pipe(replace('SF:.', 'SF:BAM'))
        .pipe(gulp.dest('reports/coverage'));
};

gulp.task('test', function () {
    //run the tests and perform postprocessing
    karma.start({
        configFile: __dirname + '/src/test/karma.conf.ci.js'
    }, postprocessLCOV);
});