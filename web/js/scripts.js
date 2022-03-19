/*!
 * Start Bootstrap - SB Admin v7.0.4 (https://startbootstrap.com/template/sb-admin)
 * Copyright 2013-2021 Start Bootstrap
 * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
 */
// 
// Scripts
// 

window.addEventListener('DOMContentLoaded', event => {

    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }
});
$(document).ready(function () {
    $('.confirm-delete').on('click', function (e) {
        e.preventDefault();

        var id = $(this).attr("id");

        $("#myModal").data('id', id).modal('show');
    });

    $('#btnDeleteServer').click(function () {
        var id = $('#myModal').data('id');
        var servletContext = document.getElementById("servletContext").value;
        var url = servletContext + '/server/delete';
        $.post(url, {id: id}, function () {
            $('#error-alert').addClass('d-none');
            $('#success-alert').removeClass('d-none');
            $('#rowid' + id).remove();
            $('#alertSuccessInfo').html("Delete server successfully");
        })
                .fail(function () {
                    $('#success-alert').addClass('d-none');
                    $('#error-alert').removeClass('d-none');
                    $('#alertErrorInfo').html("Delete server failed");
                });
        $('#myModal').modal('hide');
    });

    $('#btnDeleteMatchOrder').click(function () {
        var id = $('#myModal').data('id');
        var servletContext = document.getElementById("servletContext").value;
        var url = servletContext + '/match/order/delete';
        $.post(url, {id: id}, function () {
            $('#error-alert').addClass('d-none');
            $('#success-alert').removeClass('d-none');
            $('#rowid' + id).remove();
            $('#alertSuccessInfo').html("Delete match order successfully");
        })
                .fail(function () {
                    $('#success-alert').addClass('d-none');
                    $('#error-alert').removeClass('d-none');
                    $('#alertErrorInfo').html("Delete match order failed");
                });
        $('#myModal').modal('hide');
    });

    $('#deleteAccountbtn').click(function () {
        var id = $('#myModal').data('id');
        var servletContext = document.getElementById("servletContext").value;
        var url = servletContext + '/account/delete';
        $.post(url, {id: id}, function () {
            $('#error-alert').addClass('d-none');
            $('#success-alert').removeClass('d-none');
            $('#rowid' + id).remove();
            $('#alertSuccessInfo').html("Delete account successfully");
        })
                .fail(function () {
                    $('#success-alert').addClass('d-none');
                    $('#error-alert').removeClass('d-none');
                    $('#alertErrorInfo').html("Delete account failed");
                });
        $('#myModal').modal('hide');
    });

    $('#btnDeleteMatchHistory').click(function () {
        var id = $('#myModal').data('id');
        var servletContext = document.getElementById("servletContext").value;
        var url = servletContext + '/match/history/delete';
        $.post(url, {id: id}, function () {
            $('#error-alert').addClass('d-none');
            $('#success-alert').removeClass('d-none');
            $('#rowid' + id).remove();
            $('#alertSuccessInfo').html("Delete match successfully");
        })
                .fail(function () {
                    $('#success-alert').addClass('d-none');
                    $('#error-alert').removeClass('d-none');
                    $('#alertErrorInfo').html("Delete match failed");
                });
        $('#myModal').modal('hide');
    });

    $('#genAPIkeybtn').on('click', function () {
        $('#apikeyInput').val(generateAPIKey());
    });

    $('#editAccountSubmit').on('click', function (e) {
        e.preventDefault();
        var password = $('#passwordInput').val();
        if (password === '')
        {
            $('#passwordInput').removeAttr('name');
            $('#ConfirmPasswordInput').removeAttr('name');
        }

        $('#editAccountForm').submit();
    });
});

function generateAPIKey()
{
    var d = new Date().getTime();

    if (window.performance && typeof window.performance.now === "function")
    {
        d += performance.now();
    }

    var apikey = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c)
    {
        var r = (d + Math.random() * 16) % 16 | 0;
        d = Math.floor(d / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });

    return apikey;
}

