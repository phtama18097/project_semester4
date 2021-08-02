"use strict";

$("#table-admin").dataTable({
   "responsive": true,
   "order": [[0, "desc"]],
   "dom": '<"pull-left"B><"pull-right"f>rt<"row"<"col-sm-4"l><"col-sm-4"i><"col-sm-4"p>>',
    "buttons": [
        {
            extend: 'excel',
            text: '<i class="fas fa-file-excel"></i> Excel',
            exportOptions: {
                modifier: {
                    page: 'current'
                }
            },
            className: 'btn btn-success btn-sm'
        },
        {
            extend: 'pdf',
            text: '<i class="fas fa-file-pdf"></i> PDF',
            exportOptions: {
                modifier: {
                    page: 'current'
                }
            },
            className: 'btn btn-danger btn-sm'
        },
        {
            extend: 'print',
            text: '<i class="fas fa-print"></i> Print',
            exportOptions: {
                modifier: {
                    page: 'current'
                }
            },
            className: 'btn btn-info btn-sm'
        }
    ],
  "lengthMenu": [[10, 25, 50, 100, -1], [10, 25, 50, 100, "All"]]
});
