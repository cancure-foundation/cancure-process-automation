var gulp = require('gulp'),
    concat = require('gulp-concat'),
    connect = require('gulp-connect'),
    uglify = require('gulp-uglify'),
    sass = require('gulp-sass'),
    cleanCSS = require('gulp-clean-css'),
    ngAnnotate = require('gulp-ng-annotate');

/**
 *   file list
 **/
var appJs = ['app-main.js', 'app-route.js', 'modules/*/*.js', 'modules/*/*/*.js'],

    htmlList = ['index.html', 'modules/*/*.html', 'modules/*/*/*.html'],

    vendorJs = ['node_modules/angular/angular.min.js', 'node_modules/angular-ui-router/release/angular-ui-router.min.js', 'node_modules/angular-animate/angular-animate.min.js', 'node_modules/angular-aria/angular-aria.min.js', 'node_modules/angular-material/angular-material.min.js', 'vendor/jquery.js'],

    appSass = ['modules/*.scss', 'modules/*/*.scss', 'modules/*/*/*.scss'],

    buildFiles = ['index.html', 'images/**', 'build/**', 'modules/**', '!modules/*.scss', '!modules/*/*.scss', '!modules/*/*/*.scss', '!modules/*/*.js', '!modules/*/*/*.js'];


/**
 * task to compile all app JS files in the project folder
 * outputs the concatinated files to build/app.js
 **/
gulp.task('app-js', function () {
    return gulp.src(appJs)
        .pipe(ngAnnotate())
        //.pipe(uglify())
        .pipe(concat('app.js'))
        .pipe(gulp.dest('build/js'));
});

/**
 * task to compile all vendor JS files in the project folder
 * outputs the concatinated files to build/vendor.js
 **/
gulp.task('vendor-js', function () {
    return gulp.src(vendorJs)
        .pipe(concat('vendor.js'))
        .pipe(gulp.dest('build/js'));
});

gulp.task('js', ['app-js', 'vendor-js']);

/**
 * task to compile all SCSS files in the project folder
 * outputs the concatinated files to build/style.css
 **/
gulp.task('sass', function () {
    return gulp.src('modules/style.scss')
        .pipe(sass())
        .pipe(cleanCSS())
        .pipe(concat('style.css'))
        .pipe(gulp.dest('build/css'));
});

/**
 * task to WATCH all registered files
 **/
gulp.task('watch', function () {
    gulp.watch(appSass, ['sass', 'build']);
    gulp.watch(appJs, ['js', 'build']);
    gulp.watch(htmlList, ['build']);
});

/**
 * task to start server
 **/
gulp.task('connect', function () {
    connect.server({
        root: '../app',
        livereload: true,
        port: 8000
    })
});

/**
 * default gulp task
 **/
gulp.task('run', ['js', 'sass', 'watch']);
/*
gulp.task('run', ['js', 'sass', 'build', 'watch', 'connect']);
*/


/**
 * gulp build
 **/
gulp.task('build', function () {
    return gulp.src(buildFiles, {
            base: './'
        })
        .pipe(gulp.dest('../www/'));
});