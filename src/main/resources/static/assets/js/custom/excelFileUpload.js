$(document).ready(function() {

	  var table = $('#file-data-table').DataTable({
	        "paging": true,
	        "lengthChange": true,
	        "searching": true,
	        "ordering": true,
	        "info": false,
	        "responsive": true,
	        "autoWidth": false,
	        "pageLength": 10,
	        "ajax" : {
						"url" : 'getFileData.php',
						"type" : "POST",
						"data" : {
						"action" : "getData"
					}
			},
	        "columns": [
	                    
	        {"data": "nick_name"},
	        {"data":"file_name"},
	        //{"data":"path"},
	        {"data":"file_upload_date_time"},
	        {"data":"status"},
	        {"data":"mapping"}
	        ]
	      });

var $loading = $('#loading').hide();
                   //Attach the event handler to any element
$(document).ajaxStart(function () {
                        //ajax request went so show the loading image
                         $loading.show();
                     })
                   .ajaxStop(function () {
                       //got response so hide the loading image
                        $loading.hide();
                    });
$("#submit-btn").click(function() {
		var file_data = $('#file-input').prop('files')[0];   
		var form_data = new FormData();                  
		form_data.append('file', file_data);
		form_data.append('name', $('#nick-name').val());                            
		$.ajax({
	       url: 'excelFileUpload.php', // point to server-side PHP script 
	      // dataType: 'text',  // what to expect back from the PHP script, if anything
	       cache: false,
	       contentType: false,
	       processData: false,
	       data: form_data,                         
	       type: 'post',
	       success: function(php_script_response){
	        	clear();
	           alert(php_script_response); // display response from the PHP script, if an
	           table.ajax.reload(null, false);
	       }
	   });

	});

	
	$(document).on('click','#map-data',function(){
		/*var id = $(this).val();
		$.ajax({
	       url: 'viewExcelMapping.php', // point to server-side PHP script 
	      // dataType: 'text',  // what to expect back from the PHP script, if anything
	       cache: false,
	       contentType: false,
	       processData: false,
	       data: {
	       	excelFile : id
	       },                         
	       type: 'post'
	   });*/
		window.location = 'viewExcelMapping.php?excelFile='+$(this).val();
	});


	$(document).on('click','#reset',function(){
		clear();
	});

});


function clear(){
	$('#file-input').val('');
	$('#nick-name').val('');
}
