$(document).ready(function () {
    updateDashboard();
    fillCategoriesBox();
});

$('#settings').on('click', function (e) {
    e.preventDefault();
    $('#wallets-page').removeClass('hide');
    $('#home').removeClass('hide');
    $('#main-page').addClass('hide');
    
});

$('#home').on('click', function (e) {
    e.preventDefault();
    $('#main-page').removeClass('hide');  
    $('#home').addClass('hide');
    $('#wallets-page').addClass('hide');
      
});

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

        var month = "0" + (d.getMonth() + 1);
        month = month.slice(month.length - 2);

        var day = "0" + d.getDate();
        day = day.slice(day.length - 2);

        var date = month + "/" + day + "/" + d.getFullYear();
        var time = d.toLocaleString('en-US', {
            hour: 'numeric',
            minute: 'numeric',
            hour12: true
        });

        return date + " " + time;
    },

    listTransactions: function (result) {
        var place = $('#dash');
        place.html('');

        if (result !== '') {
            var income = 0;
            var expense = 0;
            $.each(result, function (k, v) {
                var colorClass = null;
                if (v.category.type.id === 1) {
                    colorClass = "income";
                    income += v.amount;
                } else if (v.category.type.id === 2) {
                    colorClass = "expense";
                    expense += v.amount;
                }
                var date = helpers.formatDate(v.time);
                place.append('<div class="horizontal-flex-box width-100 ' + colorClass + '" value="' + v.id + '"><div class="tenth">' + v.category.name + '</div><div class="third">' + v.notes + '</div><div class="fifth">' + date + '</div><div class="tenth">' + v.wallet.name + '</div><div class="tenth colored">' + v.amount + '</div></div>');
                helpers.updateChart(income, -expense);
            });
        }
    },

    updateChart: function (income, expense) {
        var incomePercentage = Math.round(income / (income + expense) * 100);
        var max = Math.max(income, expense);

        var width = $("#full-dash").width() - $("#dash-form").width();

        function createChart() {
            $("#dash-chart").kendoChart({
                chartArea: {
                    width: width,
                    height: 40
                },
                legend: {
                    visible: false
                },
                seriesDefaults: {
                    type: "bar",
                    stack: {
                        type: "100%"
                    }
                },
                series: [{
                    name: "Income",
                    data: [income],
                    color: "#4caf50"
                }, {
                    name: "Expense",
                    data: [expense],
                    color: "#ee5315"
                }],
                valueAxis: {
                    visible: false
                },
                categoryAxis: {
                    visible: false
                },
                tooltip: {
                    visible: true,
                    template: "#= series.name #: #= value #"
                }
            });
        }

        createChart();
    },

    fillEditMenu: function (data) {
        $('#right-edit').val(data.id);
        $('#selected-amount').val(Math.abs(data.amount));
        $('#selected-category').val(data.category.id);
        $('#selected-wallet').val(data.wallet.id);
        $('.dop').val(helpers.formatDate(data.time));
        $('#selected-notes').val(data.notes);
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
                if (v.type.id === 1) {
                    list = listIncome;
                } else {
                    list = listExpense;
                }
                var html = "";
                html += '<div class="category-style" value="' + v.id + '">';
                html += '<div class="category-name">' + v.name + '<i class="fas fa-pencil-alt pencil"></i></div>';
                html += '<form class="edit-category-form hide" method="PUT" action="/mywallet/categories/update/' + v.id + '">';
                html += '<input class="edit-category-name" name="name" type="text" value="' + v.name + '"></input>';
                html += '<button class="save-edit-category tiny-margin no-stretch"><i class="far fa-save"></i></button>';
                html += '<button class="delete-category tiny-margin no-stretch">';
                html += '<i class="fas fa-trash-alt"></i>';
                html += '</button>'
                html += '</form>'
                html += '</div>'
                list.prepend(html);
                initializeCallbacks();
            });
        }
    }
};

function initializeCallbacks() {
    $(".pencil").on('click', function (e) {
        $(e.target).parent().addClass('hide');
        // debugger;
        $(e.target).parent().next().removeClass('hide');
    });
}


$("#add-transaction-form").submit(function (e) {
    var data = $(this).serialize();
    var url = $(this).attr('action');
    console.log($(this));
    console.log(url);
    console.log(data);

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
    $('.dtp').val(helpers.formatDate(new Date()));
});

$('#edit-transaction-button').on('click', function (e) {
    e.preventDefault();

    var form = $(this).closest('form');
    var data = form.serialize();
    console.log(data);
    var url = '/mywallet/transactions/update/' + $('#right-edit').val();

    $.ajax({
        type: "PUT",
        url: url,
        data: data,
        success: function () {
            updateDashboard();
        }
    });
});

$('#delete-transaction-button').on('click', function (e) {
    e.preventDefault();
    var url = '/mywallet/transactions/delete/' + $('#right-edit').val();

    $.ajax({
        type: "DELETE",
        url: url,
        success: function () {
            updateDashboard();
            $('#right-add').removeClass('hidden');
            $('#right-edit').addClass('hidden');
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
            'All wallets'
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
            'All categories'
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
            helpers.listTransactions(data);
        }
    });
}

$('#dash').on('click', '.income, .expense', function (e) {
    e.stopPropagation();
    $('#right-edit').removeClass('hidden');
    $('#right-add').addClass('hidden');
    var id = $(this).attr('value');

    $.ajax({
        type: "POST",
        url: "/mywallet/transactions/" + id,
        success: function (data) {
            helpers.fillEditMenu(data);
        }
    })
});

$('#center').on('click', function () {
    $('#right-add').removeClass('hidden');
    $('#right-edit').addClass('hidden');
});

function fillCategoriesBox() {
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
}

$("#add-expense-category").on('click', function (e) {
    var url = "/mywallet/categories/add";
    var data = "name=New%20Category&typeId=2";
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function () {
            fillCategoriesBox();
        }
    });

    e.preventDefault();
    $(this).trigger('reset');
});

$('#dash-select-wallet, #dash-select-category, #from-date, #to-date').on('change', submitDash);

function submitDash() {
    var $form = $('#dash-form');
    var url = $form.attr('action');
    var data = $form.serialize();
    console.log(data);

    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function (result) {
            helpers.listTransactions(result);
        }
    });
}


$("#add-income-category").on('click', function (e) {
    var url = "/mywallet/categories/add";
    var data = "name=New%20Category&typeId=1";
    $.ajax({
        type: "POST",
        url: url,
        data: data,
        success: function () {
            fillCategoriesBox();
        }
    });

    e.preventDefault();
    $(this).trigger('reset');
});


$('.save-edit-category').on('submit', function (e) {
    e.preventDefault();

    var form = $(this).closest('form');
    var text = $(this).closest('input').attr('type');
    var data = form.serialize();
    console.log(data);
    var url = '/mywallet/categories/update/' + $('.category-style').val() + 'name=' + text;
    console.log('category id is ' + $('.category-style').val());

    $.ajax({
        type: "PUT",
        url: url,
        data: data,
        success: function () {
            fillCategoriesBox();
        }
    });
});
var serialize = function (obj) {
    var str = [];
    for (var p in obj)
        if (obj.hasOwnProperty(p)) {
            str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        }
    return str.join("&");
};

$(function () {
    var walletsBaseUrl = "/mywallet/wallets",
        walletsDataSource = new kendo.data.DataSource({
            transport: {
                read: {
                    url: walletsBaseUrl + "/",
                },
                update: {
                    type: "PUT",
                    url: walletsBaseUrl + "/update",
                },
                destroy: {
                    type: "POST",
                    url: walletsBaseUrl + "/delete",
                },
                create: {
                    type: "POST",
                    url: walletsBaseUrl + "/add",
                },
                parameterMap: function (data, type) {
                    if (type == "update") {
                        return {
                            id: data.id,
                            name: data.name,
                            balance: data.balance
                        }
                    } else if (type == "destroy") {
                        return {
                            id: data.id
                        }
                    } else if (type == "create") {
                        return {
                            name: data.name,
                            balance: data.balance
                        }
                    }
                }
            },
            batch: false,
            autoSync: true,
            pageSize: 20,
            schema: {
                model: {
                    id: "id",
                    fields: {
                        id: {
                            editable: false
                        },
                        name: {
                            validation: {
                                required: true
                            }
                        },
                        balance: {
                            type: "number",
                            validation: {
                                required: true
                            }
                        }
                    }
                },
            }
        });



    $("#wallets-grid").kendoGrid({
        dataSource: walletsDataSource,
        pageable: true,
        height: 550,
        toolbar: [{
            name: "create",
            text: "Add new wallet"
        }],
        columns: [{
                field: "name",
                title: "Wallet Name"
            },
            {
                field: "balance",
                title: "Balance",
                width: "120px"
            },
            {
                command: "destroy",
                title: " ",
                width: "150px"
            }
        ],
        editable: true
    });
});

$(function () {
    var dataSource = new kendo.data.DataSource({
        transport: {
            read: {
                url: "/mywallet/wallets/",
            }
        },
        pageSize: 5
    });

    $("#walletsPager").kendoPager({
        dataSource: dataSource
    });

    $("#walletsListView").kendoListView({
        dataSource: dataSource,
        template: kendo.template($("#walletTemplate").html())
    });
});

$(document).ready(function () {
    var dataSource = new kendo.data.DataSource({
        pageSize: 20,
        transport: {
            read: {
                url: "/mywallet/categories/",
            },
            update: {
                type: "PUT",
                url: "/mywallet/categories/update",
            },
            destroy: {
                type: "POST",
                url: "/mywallet/categories/delete",
            },
            create: {
                type: "POST",
                url: "/mywallet/categories/add",
            },
            parameterMap: function (data, type) {
                if (type == "update") {
                    return {
                        id: data.id,
                        name: data.name,
                        typeId: data.type.id
                    }
                } else if (type == "destroy") {
                    return {
                        id: data.id
                    }
                } else if (type == "create") {
                    console.log(data.name + "   " + data.type.id);
                    return {
                        name: data.name,
                        typeId: data.type.id
                    }
                }
            }
        },
        batch: false,
        autoSync: true,
        schema: {
            model: {
                id: "id",
                fields: {
                    id: {
                        editable: false
                    },
                    name: {
                        validation: {
                            required: true
                        }
                    },
                    type: {
                        defaultValue: {
                            id: 1,
                            name: "Income"
                        }
                    },
                }
            }
        }
    });

    $("#grid").kendoGrid({
        dataSource: dataSource,
        pageable: true,
        height: 550,
        toolbar: [{
            name: "create",
            text: "Add new category"
        }],
        columns: [{
                field: "name",
                title: "Category Name"
            },
            {
                field: "type",
                title: "Type",
                width: "180px",
                editor: categoryDropDownEditor,
                template: "#=type.name#"
            },
            {
                command: "destroy",
                title: " ",
                width: "150px"
            }
        ],
        editable: true
    });
});

function categoryDropDownEditor(container, options) {
    $('<input required name="' + options.field + '"/>')
        .appendTo(container)
        .kendoDropDownList({
            autoBind: false,
            dataTextField: "name",
            dataValueField: "id",
            dataSource: {
                transport: {
                    read: "/mywallet/types/"
                },
                schema: {
                    model: {
                        id: "id",
                        fields: {
                            id: {
                                editable: false
                            },
                            name: {
                                editable: false
                            }
                        }
                    }
                }
            }
        });
}