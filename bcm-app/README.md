Docs: https://github.com/ngbp/ngbp

# Development

$ npm -g install grunt-cli bower

- In project dir:
    $ npm install
    $ bower install
    $ grunt watch

# Start Node Server

$ node server.js

# Deployment

When you're ready to push your app into production, just run the compile command:

$ grunt compile
This will concatenate and minify your sources and place them by default into the bin/ directory. There will only be three files: index.html, your-app-name.js, and your-app-name.css. All of the vendor dependencies like Bootstrap styles and AngularJS itself have been added to them for super-easy deploying. If you use any assets (src/assets/) then they will be copied to bin/ as is.

Lastly, a complete build is always available by simply running the default task, which runs build and then compile:

$ grunt