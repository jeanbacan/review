/**
 * This file/module contains all configuration for the build process.
 */
module.exports = {
    /**
     * The `build_dir` folder is where our projects are compiled during
     * development and the `compile_dir` folder is where our app resides once it's
     * completely built.
     */
    build_dir: 'build',
    compile_dir: 'bin',

    /**
     * This is a collection of file patterns that refer to our app code (the
     * stuff in `src/`). These file paths are used in the configuration of
     * build tasks. `js` is all project javascript, less tests. `ctpl` contains
     * our reusable components' (`src/common`) template HTML files, while
     * `atpl` contains the same, but for our app's code. `html` is just our
     * main HTML file, `less` is our main stylesheet, and `unit` contains our
     * app's unit tests.
     */
    app_files: {
        js: ['src/**/*.js', '!src/**/*.spec.js', '!src/assets/**/*.js'],
        jsunit: ['src/**/*.spec.js'],

        //coffee: [ 'src/**/*.coffee', '!src/**/*.spec.coffee' ],
        //coffeeunit: [ 'src/**/*.spec.coffee' ],

        atpl: ['src/app/**/*.tpl.html'],
        ctpl: ['src/common/**/*.tpl.html'],

        html: ['src/index.html'],
        less: 'src/less/main.less'
    },

    /**
     * This is a collection of files used during testing only.
     */
    test_files: {
        js: [
            'vendor/angular-mocks/angular-mocks.js'
        ]
    },

    /**
     * This is the same as `app_files`, except it contains patterns that
     * reference vendor code (`vendor/`) that we need to place into the build
     * process somewhere. While the `app_files` property ensures all
     * standardized files are collected for compilation, it is the user's job
     * to ensure non-standardized (i.e. vendor-related) files are handled
     * appropriately in `vendor_files.js`.
     *
     * The `vendor_files.js` property holds files to be automatically
     * concatenated and minified with our project source files.
     *
     * The `vendor_files.css` property holds any CSS files to be automatically
     * included in our app.
     *
     * The `vendor_files.assets` property holds any assets to be copied along
     * with our app's assets. This structure is flattened, so it is not
     * recommended that you use wildcards.
     */
    vendor_files: {

        js: [
            'vendor/angular/angular.js',
            'vendor/jquery/dist/jquery.min.js',
            'vendor/angular-i18n/angular-locale_pt-br.js',            
            'vendor/angular-ui-router/release/angular-ui-router.js',
            'vendor/angular-ui-utils/modules/route/route.js',
            'vendor/AngularJS-Toaster/toaster.min.js',
            'vendor/angular-toastr/dist/angular-toastr.tpls.min.js',
            'vendor/angular-resource/angular-resource.js',            
            'vendor/angular-animate/angular-animate.min.js',
            'vendor/spin.js/spin.js',
            'vendor/angular-ladda/dist/angular-ladda.min.js',
            'vendor/angular-spinner/dist/angular-spinner.min.js',
            'vendor/ladda/dist/ladda.min.js',
            'vendor/angular-auto-validate/dist/jcs-auto-validate.min.js',
            'vendor/angular-jwt/dist/angular-jwt.min.js',
            'vendor/a0-angular-storage/dist/angular-storage.min.js',
            'vendor/a0-angular-storage/dist/angular-storage.min.js',
            'vendor/angular-sanitize/angular-sanitize.min.js',
            'vendor/angular-material/angular-material.min.js',
            'vendor/angular-aria/angular-aria.min.js',
            'vendor/angular-material-icons/angular-material-icons.min.js',
            'vendor/angular-material-data-table/dist/md-data-table.min.js',
            'vendor/moment/moment.js',
            'vendor/angular-moment/angular-moment.js',
            'vendor/zingchart/client/zingchart.min.js',
            'vendor/ZingChart-AngularJS/src/zingchart-angularjs.js',
            'vendor/angular-base64/angular-base64.min.js',
            'vendor/xdLocalStorage/dist/scripts/ng-xdLocalStorage.min.js',
            'vendor/angular-material-sidemenu/dest/angular-material-sidemenu.js',
            'vendor/xdLocalStorage/dist/scripts/xdLocalStoragePostMessageApi.min.js',
            'vendor/angular-material-expansion-panel/dist/md-expansion-panel.min.js',
            'vendor/angular-material-expansion-panel/dist/md-expansion-panel.js'
        ],
        css: [
            'vendor/angular-material/angular-material.min.css',
            'vendor/angular-material-data-table/dist/md-data-table.min.css',            
            'vendor/angular-toastrs/dist/angular-toastr.min.css',
            'vendor/angular-toastr/dist/angular-toastr.css',
            'vendor/angular-material-sidemenu/dest/angular-material-sidemenu.css',
            'vendor/angular-material-expansion-panel/dist/md-expansion-panel.css',
            'vendor/angular-material-expansion-panel/dist/md-expansion-panel.min.css'
        ],
        assets: []
    }
};




