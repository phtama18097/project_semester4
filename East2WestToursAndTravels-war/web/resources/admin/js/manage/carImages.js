/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

const input = document.getElementById('customFile')

input.addEventListener('change', (event) => {
  const target = event.target
  	if (target.files && target.files[0]) {

      const maxAllowedSize = 5 * 1024 * 1024;
      if (target.files[0].size > maxAllowedSize) {
      	// Here you can ask your users to load correct file
       	target.value = ''
        $('#filemessage').text('File size must be less than 5MB.');
      }
  }
})

$(function () {
    $('input:file').on('change', function () {
        $.each(this.files, (i, v) => {
            var filename = v.name;
            $('.filenames').append('<span class="label label-info label-inline mr-2">'+filename+'</span>');

        });
    });
});


// Confirm delete object
function confirmDelete(id) {
    Swal.fire({
        title: "Are you sure?",
        text: 'You won"t be able to revert this!',
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, cancel!",
        reverseButtons: true
    }).then(function (result) {
        if (result.value) {
            $('#btnDelete-' + id).click();
        }
    });
}

// Detail object
function detailObject(id, filename) {
    $('#dtID').val(id);
    $('#dtThumbnail').attr('src', "/East2WestToursAndTravels-war/uploads/imgCars/" + filename);
    $('#detailModal').modal();
}