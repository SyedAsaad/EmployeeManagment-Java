$(document).ready(function() {

      getAllData();
      loadFileDropDown();
      var marketId = 0;

      $('#marketing-data-table tbody').on('click', 'td.details-control',
                      function() {
                          var tr = $(this).closest('tr');
                          var row = $('#marketing-data-table').DataTable().row(tr);

                          if (row.child.isShown()) {
                              // This row is already open - close it
                              row.child.hide();
                              tr.removeClass('shown');
                          } else {
                              // Open this row
                              row.child(format(row.data())).show();
                              tr.addClass('shown');
                          }
      });
        

        $(document).on('click','#delete-data',function(){
            var form_data = new FormData();
            form_data.append("action","delete");
            form_data.append("marketingId",$(this).val());
            
            alertify.confirm('Confirm', 'Are you sure you want to delete ?', function(){ 
                $.ajax({
                    url: 'dataOperations.php',
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: form_data,                         
                    type: 'post',
                    success: function(php_script_response){
                         alert(php_script_response);
                         $('#marketing-data-table').DataTable().ajax.reload( null, false );
                    }
                });
            }
            , function(){ alertify.error('Cancel')	
            }
            
            );
        });

        $(document).on('click','#update-data',function(){
            var form_data = new FormData();
            form_data.append("action","update");
            form_data.append("marketingId",marketId);
            form_data.append("first_name",$("#first-name").val());
            form_data.append("last_name",$("#last-name").val());
            form_data.append("property_address",$("#property-address").val());
            form_data.append("property_city",$("#property-city").val());
            form_data.append("property_address2",$("#property-address2").val());
            form_data.append("property_state",$("#property-state").val());
            form_data.append("property_zip",$("#property-zip").val());
            form_data.append("mailing_address",$("#mailing-address").val());
            form_data.append("mailing_address2",$("#mailing-address2").val());
            form_data.append("mailing_city",$("#mailing-city").val());
            form_data.append("mailing_state",$("#mailing-state").val());
            form_data.append("mailing_zip",$("#mailing-zip").val());

            
            $.ajax({
                    url: 'dataOperations.php',
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: form_data,                         
                    type: 'post',
                    success: function(data){
                         alert(data);
                         clear();
                         $('#marketing-data-table').DataTable().ajax.reload( null, false );
                    }
                });
        });


        $(document).on('click','#edit-data',function(){
            var form_data = new FormData();
            form_data.append("action","edit");
            marketId = $(this).val();
            form_data.append("marketingId",$(this).val());
            
            $.ajax({
                    url: 'dataOperations.php',
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: form_data,                         
                    type: 'post',
                    success: function(data){
                         $("#first-name").val(data[0].First_Name);
                         $("#last-name").val(data[0].Last_Name);
                         $("#property-address").val(data[0].Property_Address);
                         $("#property-address2").val(data[0].Property_Address_2);
                         $("#property-city").val(data[0].Property_City);
                         $("#property-state").val(data[0].Property_State);
                         $("#property-zip").val(data[0].Property_Zip);
                         $("#mailing-address").val(data[0].Mailing_Address);
                         $("#mailing-address2").val(data[0].Mailing_Address_2);
                         $("#mailing-city").val(data[0].Mailing_City);
                         $("#mailing-state").val(data[0].Mailing_State);
                         $("#mailing-zip").val(data[0].Mailing_Zip);
                         $(window).scrollTop($("#anchor").offset().top);
                    }
                });
        });


        $(document).on('click','#all-data-btn',function(){
            $('#marketing-data-table').DataTable().destroy();
            getAllData();
        });

        $(document).on('click','#file-data-btn',function(){
            if($("#file-dropdown").val() != 0){
            $('#marketing-data-table').DataTable().destroy();
            getFileWiseData();
          }
          else{
            alert("Please select file");
          }
        });


        $(document).on('click','#reset',function(){
            clear();
        });


});

function getFileWiseData(){
    
        $('#marketing-data-table').DataTable({
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
                        "action" : "getFileWiseData",
                        "file_id":$("#file-dropdown").val()
                    }
            },
            "columns" : [ {
              "className" : 'details-control',
              "orderable" : false,
              "data" : null,
              "defaultContent" : ''
          },
    
          {
              "data" : "First Name"
          }, {
              "data" : "Last Name"
          }, {
              "data" : "file_name"
          }, {
              "data" : "edit"
          }, {
              "data" : "delete"
          }
    
          ],
          "order" : [ [ 1, 'asc' ] ]
          });
    
}


   
function getAllData(){
    $('#marketing-data-table').DataTable({
        "paging": true,
        "lengthChange": true,
        "searching": true,
        "ordering": true,
        "info": false,
        "responsive": true,
        "autoWidth": false,
        "pageLength": 10,
      "order" : [ [ 1, 'asc' ] ]
      });
}



function format(d) {
    // `d` is the original data object for the row
    return '<table cellpadding="0" cellspacing="0" border="0" style="padding-left:0px;">'
            + '<tr>'
            + '<td>Property Address</td>'
            + '<td>'+d.Property_Address+'</td>'
            + '</tr>'
            + '<tr>'
            + '<td>Property Address 2</td>'
            + '<td>'+d.Property_Address_2+'</td>'
            + '</tr>'
            + '<tr>'
            + '<td>Property City</td>'
            + '<td>'+d.Property_City+'</td>'
            + '</tr>'
            + '<tr>'
            + '<td>Property State</td>'
            + '<td>'+d.Property_State+'</td>'
            + '</tr>'
            + '<tr>'
            + '<td>Property Zip</td>'
            + '<td>'+d.Property_Zip+'</td>'
            + '</tr>'
            + '<tr>'
            + '<td>Mailing Address</td>'
            + '<td>'+d.Mailing_Address+'</td>'
            + '</tr>'
            + '<tr>'
            + '<td>Mailing Address 2</td>'
            + '<td>'+d.Mailing_Address_2+'</td>'
            + '</tr>'
            + '<tr>'
            + '<td>Mailing City</td>'
            + '<td>'+d.Mailing_City+'</td>'
            + '</tr>'
            + '<tr>'
            + '<td>Mailing State</td>'
            + '<td>'+d.Mailing_State+'</td>'
            + '</tr>'
            + '<tr>'
            + '<td>Mailing Zip</td>'
            + '<td>'+d.Mailing_Zip+'</td>'
            + '</tr>'
            
            + '</table>';
}


function loadFileDropDown(){
          var form_data = new FormData();
          form_data.append("action","getFileData");
          
          $.ajax({
                    url: 'dataOperations.php',
                    cache: false,
                    contentType: false,
                    processData: false,
                    data: form_data,                         
                    type: 'post',
                    success: function(data){

                      for(var x in data){
                         $("#file-dropdown").append("<option value='"+data[x].file_detail_id+"'>"+data[x].file_name+"</option>").trigger(
            "chosen:updated");
                       }
                    }
                });
}

function clear(){
    $("#first-name").val('');
    $("#last-name").val('');
    $("#property-address").val('');
    $("#property-address2").val('');
    $("#property-city").val('');
    $("#property-state").val('');
    $("#property-zip").val('');
    $("#mailing-address").val('');
    $("#mailing-address2").val('');
    $("#mailing-city").val('');
    $("#mailing-state").val('');
    $("#mailing-zip").val('');
}