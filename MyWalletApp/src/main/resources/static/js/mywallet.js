$(document).ready(function () {
    updateDashboard();
});

$(function () {
    $('#main-page').show();
    $('#show-main').addClass('active');

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

    formatDate: function (dateToFormat) {
        var d = new Date(dateToFormat);

        var date = d.getDate() + "/" + d.getMonth() + "/" + d.getFullYear();        
        var time = d.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });

        var result = date + " " + time;
        return result;
    },

    listTransactions: function (result, place) {
        place.html('');
        if (result !== '') {
            $.each(result, function (k, v) {
                var colorClass = null;
                if (v.category.type.id == 1) colorClass = "income";
                else if (v.category.type.id == 2) colorClass = "expense";
                var date = helpers.formatDate(v.time);
                place.prepend('<div class="horizontal-flex-box width-100 ' + colorClass + '" value="' + v.id + '"><div class="tenth">' + v.category.name + '</div><div class="tenth">' + v.notes + '</div><div class="fifth">' + date + '</div><div class="tenth">' + v.wallet.name + '</div><div class="tenth">' + v.amount + '</div></div>');
            });
        };
        place.prepend('<div id="headers" class="horizontal-flex-box headers width-100"><div class="tenth">Category</div><div class="fifth">Notes</div><div class="tenth">Date</div><div class="tenth">Wallet</div><div class="tenth">Amount</div></div>')
    },

    fillEditMenu: function(data){
        $('#right-edit').val(data.id);
        $('#selected-amount').val(data.amount);
        $('#selected-category').val(data.category.id);
        $('#selected-wallet').val(data.wallet.id);
        $('.dop').val(helpers.formatDate(data.time));
        $('#selected-notes').val(data.notes);
    }
};

$("#add-transaction-form").submit(function (e) {
    var data = $(this).serialize();
    var url = $(this).attr('action');

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function () {
            updateDashboard();
        }
    });

    e.preventDefault();
    $(this).trigger('reset');
});

$('#edit-transaction-button').on('click', function(e){
    e.preventDefault();

    var form = $(this).closest('form');
    var data = form.serialize();
    console.log(data);
    console.log('Transaction id is'+$('#right-edit').val());
    var url = '/mywallet/transactions/update/'+$('#right-edit').val();

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function () {
            updateDashboard();
        }
    });
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
            $('#select-wallet, #selected-wallet'),
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
            $('#select-category, #selected-category'),
            'Select a category'
        );
    }
});

function updateDashboard() {
    $.ajax({
        type: "POST",
        url: "/mywallet/transactions/",
        success: function (data) {
            helpers.listTransactions(data, $('#dash'));
        }
    });
};

$('#dash').on('click', '.income, .expense', function (e) {
    e.stopPropagation();
    $('#right-edit').removeClass('hidden');
    $('#right-add').addClass('hidden');
    var id = $(this).attr('value');

    $.ajax({
        type: "POST",
        url: "/mywallet/transactions/"+id,
        success: function (data) {
            helpers.fillEditMenu(data);
        }
    })
});

$('#center').on('click', function (e) {
    $('#right-add').removeClass('hidden');
    $('#right-edit').addClass('hidden');
});