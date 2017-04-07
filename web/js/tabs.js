/**
 * Created by zgraham on 4/7/17.
 */

$(document).ready(function() {
    //Whenever one of the tabs are clicked make sure the rest are deactivated
    // and then add active to the one that was clicked
    $('.nav.nav-tabs > li').on('click', function (e) {
        e.preventDefault();
        $('.nav.nav-tabs > li').removeClass('active');
        $(this).addClass('active');
        console.log($(this).find("a").attr("href"));
        var id = $(this).attr("id");

        $(".frame").hide();
        $("#f" + id).show();

    });

});
