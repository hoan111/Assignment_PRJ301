/*!
 * Start Bootstrap - SB Admin v7.0.4 (https://startbootstrap.com/template/sb-admin)
 * Copyright 2013-2021 Start Bootstrap
 * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
 */
// 
// Scripts
// 

$(document).ready(function () {
    //Validate form
    jQuery.validator.addMethod("validateIP", function (value, element) {
        if (/\b\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}\b/.test(value))
        {
            return true;
        } else
        {
            return false;
        }
    }, 'Please enter a valid IP address');

    jQuery.validator.addMethod("valueNotEquals", function (value, element, arg) {
        return arg != value;
    }, "You must select an item!");

    $('form').each(function () {
        $(this).validate({
            rules: {
                "serverName": {
                    required: true
                },
                "serverPort": {
                    required: true,
                    digits: true,
                    maxlength: 5
                },
                "serverIP": {
                    validateIP: true,
                    required: true
                },
                "serverPassword": {
                    required: true
                },
                "rconPassword": {
                    required: true
                },
                "selectServer": {
                    valueNotEquals: "-1"
                },
                "selectMatchType": {
                    valueNotEquals: "-1"
                },
                "facebookLink": {
                    url: true
                },
                "price": {
                    required: true,
                    number: true,
                    min: 0.0
                },
                "selectMatchStatus": {
                    valueNotEquals: "-1"
                },
                "Username": {
                    required: true
                },
                "Password": {
                    required: true
                },
                "role": {
                    valueNotEquals: "-1"
                },
                "ConfirmPassword": {
                    required: true,
                    equalTo: '#passwordInput'
                },
                "passwordConfirmEdit": {
                    equalTo: '#passwordInput'
                }
            },
            errorElement: 'em',
            errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.next().append(error);
            },
            highlight: function (element, errorClass, validClass) {
                $(element).addClass('is-invalid');
            },
            unhighlight: function (element, errorClass, validClass) {
                $(element).removeClass('is-invalid');
                $(element).addClass('is-valid');
            }
        });
    });
});