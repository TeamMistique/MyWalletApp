//this is java script code :))

$(function() {
    $('#main-page').show();
    $('#main-page').addClass('active');
    
    $('.sidenav a').on('click', function(e) {
      e.preventDefault();
      $('.sidenav a').removeClass('active');
      $(this).addClass('active');
      
      var id = $(this).attr('id');
      var menu = id.substring(0,4);
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
    buildDropdown: function(result, dropdown, emptyMessage){
        //Remove current options
        dropdown.html('');

        //Add the empty option with the empty message
        dropdown.append('<option value="">'+emptyMessage+'</option>');

        //Check result isn't empty
        if(result != ''){
            //Loop through each of the results and append the option to the html
            $.each(result, function(k,v){
                
                dropdown.append('<option value="' + v.id + '">' + v.name + '</option>');
            });
        }
    }
}


$.ajax({
    type: "POST",
    url: "/mywallet/wallets/",
    success: function(data){
        debugger;
        helpers.buildDropdown(
            data,
            $('#dash-select-wallet'),
            'Select a wallet'
        );
    }
});