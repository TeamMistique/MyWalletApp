//this is java script code :))

$(function () {
    $('#main-page').show();
    $('#main-page').addClass('active');

    $('.sidenav a').on('click', function (e) {
        e.preventDefault();
        $('.sidenav a').removeClass('active');
        $(this).addClass('active');

        var id = $(this).attr('id');
        var menu = id.substring(5);
        $('body > div').hide();
        $('#' + menu + '-page').show();

        closeNav();
    })
});

function openNav() {
    document.getElementById("sidenav").style.width = "250px";
}

function closeNav() {
    document.getElementById("sidenav").style.width = "0";
}

var helpers = {
    buildDropdown: function (result, dropdown, emptyMessage) {
        //Remove current options
        dropdown.html('');

        //Add the empty option with the empty message
        dropdown.append('<option value="">' + emptyMessage + '</option>');

        //Check result isn't empty
        if (result !== '') {
            //Loop through each of the results and append the option to the html
            $.each(result, function (k, v) {
                dropdown.append('<option value="' + v.id + '">' + v.name + '</option>');
            });
        }
    },

    listTransactions: function(result, place){
        place.html('');
        if (result !== ''){
            $.each(result, function(k, v){
                place.append('<div value="'+v.id+'"><div>'+v.category.name+'</div><div>'+v.time+'</div><div>'+v.wallet.name+'</div><div>'+v.amount+'</div></div>');
            });
        };
    }
};

$("#add-transaction-form").submit(function(e) {
    var result = $(this).serialize();
    var url = $(this).attr('action');

    $.ajax({
        type: "POST",
        url: url,
        data: result
    });

    e.preventDefault();
    $(this).trigger('reset');
});

$.ajax({
    type: "POST",
    url: "/mywallet/wallets/",
    success: function (data) {
        helpers.buildDropdown(
            data,
            $('#dash-select-wallet'),
            'Select a wallet'
        );
    }
});

$.ajax({
    type: "POST",
    url: "/mywallet/wallets/",
    success: function (data) {
        helpers.buildDropdown(
            data,
            $('#select-wallet'),
            'Select a wallet'
        );
    }
});

$.ajax({
    type: "POST",
    url: "/mywallet/categories/",
    success: function (data) {
        helpers.buildDropdown(
            data,
            $('#dash-select-category'),
            'Select a category'
        );
    }
});

$.ajax({
    type: "POST",
    url: "/mywallet/categories/",
    success: function (data) {
        helpers.buildDropdown(
            data,
            $('#select-category'),
            'Select a category'
        );
    }
});

Date.prototype.toDateInputValue = (function () {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0, 10);
});

$(function () {
    $('#select-date').val(new Date().toDateInputValue());
});

$.ajax({
    type: "POST",
    url: "/mywallet/transactions/",
    success: function (data) {
        helpers.listTransactions(data, $('#dash'));
    }
});