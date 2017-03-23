/**
 * Created by Ramgopal on 11-09-2016.
 */
$(document).ready(function () {
    $('.forgot-pass').click(function(event) {
        $(".pr-wrap").toggleClass("show-pass-reset");
    });

    $('.pass-reset-submit').click(function(event) {
        $(".pr-wrap").removeClass("show-pass-reset");
    });
});
