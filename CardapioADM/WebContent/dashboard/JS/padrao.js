function display_c() {
    var refresh = 1000; // Refresh rate in milli seconds
    mytime = setTimeout('display_ct()', refresh)

}

function display_ct() {

    var strcount
    var x = new Date()
    var month = x.getMonth() + 1;
    var dia = x.getDate();
    var hora = x.getHours();
    var minuto = x.getMinutes();
    var segunto = x.getSeconds();

    if (month < 10) {
        month = "0" + month;
    }

    if (dia < 10) {
        dia = "0" + dia;
    }

    if (hora < 10) {
        hora = "0" + hora;
    }

    if (minuto < 10) {
        minuto = "0" + minuto;
    }

    if (segunto < 10) {
        segunto = "0" + segunto;
    }

    var x1 = dia + "/" + month + "/" + x.getFullYear();
    x1 = x1 + " - " + hora + ":" + minuto + ":" + segunto;
    document.getElementById('ct').innerHTML = x1;

    tt = display_c();
}


$(document).ready(function () {

    $(".tab_content").hide();
    $(".tab_content:first").show();

    $("ul.tabs li").click(function () {
        $("ul.tabs li").removeClass("active");
        $(this).addClass("active");
        $(".tab_content").hide();
        var activeTab = $(this).attr("rel");
        $("#" + activeTab).fadeIn();
    });
});
