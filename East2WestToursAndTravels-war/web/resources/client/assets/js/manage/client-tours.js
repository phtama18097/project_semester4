/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function redirectPage(id) {
    $('#btnChangeTypes-' + id).click();
}

function detailTour(id) {
    $('#btnDetail-' + id).click();
}
function addTourToCart(id) {
    $('#btnAddTourToCart-' + id).click();
}


function changePicture(data) {
    $('#productThumbnail').attr('src', "");
    $('#productThumbnail').attr('src', "/East2WestToursAndTravels-war/uploads/imgDestinations/" + data);
}
function changeCarPicture(data) {
    $('#productThumbnail').attr('src', "");
    $('#productThumbnail').attr('src', "/East2WestToursAndTravels-war/uploads/imgCars/" + data);
}
function chooseType(id) {

}

$(document).ready(function () {
    $(".product-gallery-item").click(function () {
        $(".product-gallery-item").removeClass("active");
        $(this).addClass("active");
    });
});

function changePage(id) {
    $('#btnChangePage-' + id).click();
}

function nextPage() {
    $('#btnNextPage').click();
}

function previousPage() {
    $('#btnPreviousPage').click();
}