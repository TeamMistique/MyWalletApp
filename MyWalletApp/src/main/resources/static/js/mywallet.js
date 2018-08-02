$(document).ready(function () {
    updateDashboard();
});

$(function () {
    // $('#categories-page').show();
    // $('#categories-pagee').addClass('active');
    $('body > div').hide();
    $('#categories-page').show();
    $('.sidenav a').on('click', function (e) {
        e.preventDefault();
        $('.sidenav a').removeClass('active');
        if($(this).attr('id')!== "closebtn"){
            $(this).addClass('active');
        }
        

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
        var month = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];

        var date = d.getDate() + " " + month[d.getMonth()] + ", " + d.getFullYear();
        var time = d.toLocaleTimeString().toLowerCase();

        var result = date + " at " + time;
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

    listCategories: function (result, listIncome, listExpense) {
        //Remove current options
        listIncome.html('');
        listExpense.html('');

        //Check result isn't empty
        if (result !== '') {
            //Loop through each of the results and append the div to the html
            $.each(result, function (k, v) {
                var list = null;
                if (v.type.id == 1) {
                    list = listIncome;
                } else {
                    list = listExpense;
                }
                list.append('<div class="category-style" value="' + v.id + '">' + v.name + '</div>');
            });
        }
    }

};

$("#add-transaction-form").submit(function (e) {
    var result = $(this).serialize();
    var url = $(this).attr('action');

    $.ajax({
        type: "POST",
        url: url,
        data: result,
        success: function () {
            updateDashboard();
        }
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

function updateDashboard() {
    $.ajax({
        type: "POST",
        url: "/mywallet/transactions/",
        success: function (data) {
            helpers.listTransactions(data, $('#dash'));
        }
    });
};

$.ajax({
    type: "POST",
    url: "/mywallet/categories/",
    success: function (data) {
        helpers.listCategories(
            data,
            $('#list-income-categories'),
            $('#list-expense-categories'),
        );
    }
});