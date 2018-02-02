(function () {
    'use strict';

    angular.module('cancure')
        .service('fs', fs);

    /** @ngInject */
    function fs() {
        /*jshint validthis: true*/
        var $self = this;
        $self.requestFileSystem = undefined;

        /*
         * Creates database
         * dbName {string} database name
         */
        $self.getFilesystem = function (success, fail) {
            $self.requestFileSystem = window.requestFileSystem || window.webkitRequestFileSystem;
            $self.requestFileSystem(PERSISTENT || LocalFileSystem.PERSISTENT, 1024 * 1024, success, fail);
        }

        /*
         * Creates table in db
         * tName {string} table name
         * fName {string} field names
         */
        $self.getDirectory = function (dir, options, success, fail) {

            $self.getFilesystem(function (fileSystem) {

                fileSystem.root.getDirectory(dir, options, gotDirEntry, gotDirFail);

                function gotDirEntry(dirEntry) {
                    if (success)
                        success(dirEntry);
                }

                function gotDirFail(dirEntry) {
                    if (fail)
                        fail(dirEntry);
                }
            }, function (fileSystem) {
                if (fail)
                    fail(fileSystem);
            });
        };

        /*
         * select from table in db
         * config {obj} select criteria
         * success {fn} success function
         * fail {fn} fail function
         */
        $self.moveTo = function (options, actionSuccess, actionFail) {

            var destination = options.destination;
            var fileURI = options.fileURI;
            var fileName = options.fileName;

            $self.getDirectory(destination, {
                create: true,
                exclusive: false
            }, destSuccess, actionFail);

            function destSuccess(destEntry) {
                window.resolveLocalFileSystemURL(fileURI, function (fileEntry) {
                    fileEntry.moveTo(destEntry, fileName, actionSuccess, actionFail);
                }, actionFail);
            }
        };

        /*
         * insert into table in db
         * config {obj} insert criteria
         * success {fn} success function
         * fail {fn} fail function
         */
        $self.copyTo = function (options, actionSuccess, actionFail) {

            var destination = options.destination;
            var fileURI = options.fileURI;
            var fileName = options.fileName;

            $self.getDirectory(destination, {
                create: true,
                exclusive: false
            }, destSuccess, actionFail);

            function destSuccess(destEntry) {
                window.resolveLocalFileSystemURL(fileURI, function (fileEntry) {
                    fileEntry.copyTo(destEntry, fileName, actionSuccess, actionFail);
                }, actionFail);
            }
        };

        /*
         * update a table in db
         * config {obj} insert criteria
         * success {fn} success function
         * fail {fn} fail function
         */
        $self.deleteFile = function (fileURI, actionSuccess, actionFail) {

            if (typeof (fileURI) != 'string') { // if its a list
                for (var i = 0; i < fileURI.length; i++)
                    window.resolveLocalFileSystemURL(fileURI[i], delFile, fail);

            } else {
                window.resolveLocalFileSystemURL(fileURI, delFile, fail);
            }

            function delFile(fileEntry) {
                fileEntry.remove(onDeleteSuccess, fail);
            }

            function onDeleteSuccess(file) {
                if (actionSuccess) {
                    actionSuccess(file);
                }
            }

            function fail(error) {
                if (actionFail) {
                    actionFail(error);
                }
            }
        };
    }
})();