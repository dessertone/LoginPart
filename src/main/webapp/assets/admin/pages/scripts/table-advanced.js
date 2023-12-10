var TableAdvanced = function () {
    jQuery.fn.serializeObject = function () {
        var formData = {};
        var formArray = this.serializeArray();
        for(var i = 0, n = formArray.length; i < n; ++i){
            formData[formArray[i].name] = formArray[i].value;
        }
        return formData;
    };
    var initTable1 = function () {
        var table = $('#sample_1');



        $("#record_modify_div #submit_button").click(function (){
            var sumbitdata = $("#modify_form").serializeObject()
            sumbitdata.type= "modify"
            console.log(sumbitdata)
            $.post("/UserInfo",sumbitdata,function (res)
            {
                console.log(res)
                if(res.userExist == 1)
                {
                    alert("用户名不能重复")
                }
                else
                {
                    window.location.reload()
                }
            })
        })
        $("#add_modify_div #add_button").click(function (){
            var adddata =$("#add_form").serializeObject()
            adddata.type="add"
            console.log(adddata)
            $.post("/UserInfo",adddata,function (res){
                console.log(res)
                if(res.userExist == 1)
                {
                    alert("用户名不能重复")
                }
                else
                {
                    window.location.reload()
                }
            })
        })
        $("#adduser").click(function (){
            $("#add_modify_div").modal("show")
        })

        $("#searchuser").click(function (){
            $("#search_div").modal("show")
        })



        /* Table tools samples: https://www.datatables.net/release-datatables/extras/TableTools/ */

        /* Set tabletools buttons and button container */

        var tdata = {}
        tdata.type = "getUsersList"
        var oTable = table.dataTable({
            "dom": 'Bfrtip',
            buttons: [
                {
                    extend: 'copyHtml5',
                },
                {
                    extend: 'excelHtml5',
                    title: 'Data export'
                },
                {
                    extend: 'pdfHtml5',
                    title: 'Data export'
                },
                {
                    extend: 'csvHtml5',
                    title: 'Data export'
                },
                {
                    extend:'print',
                }
            ],
            "paging":true,
            "destroy":true,
            "searching":false,
            "oLanguage": {
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                },
                "sProcessing":   "处理中...",
                "sLengthMenu":   "_MENU_ 记录/页",
                "sZeroRecords":  "没有匹配的记录",
                "sInfo":         "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
                "sInfoEmpty":    "显示第 0 至 0 项记录，共 0 项",
                "sInfoFiltered": "(由 _MAX_ 项记录过滤)",
                "sInfoPostFix":  "",
                "sSearch":       "过滤:",
                "oPaginate": {
                    "sFirst":    "首页",
                    "sPrevious": "上页",
                    "sNext":     "下页",
                    "sLast":     "末页"
                }
            },


            // Internationalisation. For more info refer to http://datatables.net/manual/i18n
            "language": {
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                },
                "emptyTable": "No data available in table",
                "info": "Showing _START_ to _END_ of _TOTAL_ entries",
                "infoEmpty": "No entries found",
                "infoFiltered": "(filtered1 from _MAX_ total entries)",
                "lengthMenu": "Show _MENU_ entries",
                "search": "Search:",
                "zeroRecords": "No matching records found"
            },

            // Or you can use remote translation file
            //"language": {
            //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
            //},

            "order": [
                [0, 'asc']
            ],
            
            "lengthMenu": [
                [5, 10, 15, 20, -1],
                [5, 10, 15, 20, "All"] // change per page values here
            ],
            // set the initial value
            "pageLength": 10,

            // "dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>", // horizobtal scrollable datatable

            // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
            // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js). 
            // So when dropdowns used the scrollable div should be removed. 
            //"dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

            "tableTools": {
                "sSwfPath": "../../assets/global/plugins/datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf",
                "aButtons": [{
                    "sExtends": "pdf",
                    "sButtonText": "PDF"
                }, {
                    "sExtends": "csv",
                    "sButtonText": "CSV"
                }, {
                    "sExtends": "xls",
                    "sButtonText": "Excel"
                }, {
                    "sExtends": "print",
                    "sButtonText": "Print",
                    "sInfo": 'Please press "CTR+P" to print or "ESC" to quit',
                    "sMessage": "Generated by DataTables"
                }]
            },
            ajax:{
                url:"/UserInfo",
                data:tdata,
                type: "POST"
            },
            "aoColumns": [{
                "mRender": function(data, type, full) {
                    return full.user_name;
                }
            },{
                "mRender": function(data, type, full) {
                    return full.nickname;
                }
            },{
                "mRender": function(data, type, full) {

                    return full.occupation;
                }
            },{
                "mRender": function(data, type, full) {

                    return full.mobile_number;
                }
            },{
                "mRender": function(data, type, full) {

                    return full.email;
                }
            },{
                "mRender": function(data, type, full) {
                    return full.create_time;
                }
            },{
                "mRender": function(data, type, full) {
                    return "<input type='hidden' name='uid' value='"+ full.user_id+"'><a class=\"edit\" href=\"javascript:;\">Edit </a>";
                }
            },{
                "mRender": function(data, type, full) {
                    return "<input type='hidden' name='uid' value='"+ full.user_id+"'><a class=\"delete\" href=\"javascript:;\">Delete </a>";
                }
            }],

        });
        $(".buttons-copy").text('复制');
        $(".buttons-excel").text('导出excel');
        $(".buttons-pdf").text('导出pdf');
        $(".buttons-csv").text('导出csv');
        $(".buttons-print").text('打印');

        table.on("click",".edit",function (e){

            var uid = $(this).prev().val()
            var email =$(this).parent().prev().prev().text()
            var phone =$(this).parent().prev().prev().prev().text()
            var occupation = $(this).parent().prev().prev().prev().prev().text()
            var nickname =$(this).parent().prev().prev().prev().prev().prev().text()
            var user_name =$(this).parent().prev().prev().prev().prev().prev().prev().text()
            console.log(user_name)
            $("#record_modify_div #uid").val(uid)
            $("#record_modify_div #user_name").val(user_name)
            $("#record_modify_div #nickname").val(nickname)
            $("#record_modify_div #occupation").val(occupation)
            $("#record_modify_div #mobile_number").val(phone)
            $("#record_modify_div #email").val(email)
            $("#record_modify_div").modal("show")
        })

        table.on("click",".delete",function (e){
            var deletedata = {}
            deletedata.type = "delete"
            deletedata.uid = $(this).prev().val()
            console.log(deletedata)
            $.post("/UserInfo",deletedata,function (res)
            {
                location.reload()
            })
        })

        $("#search_div #search_button").click(function (){
            var searchdata = $("#search_form").serializeObject()
            searchdata.type= "search"
            console.log(searchdata)
            oTable.fnDestroy();
            oTable = table.dataTable({
                dom: 'Bfrtip',
                buttons: [
                    {
                        extend: 'copyHtml5',
                    },
                    {
                        extend: 'excelHtml5',
                        title: 'Data export'
                    },
                    {
                        extend: 'pdfHtml5',
                        title: 'Data export'
                    },
                    {
                        extend: 'csvHtml5',
                        title: 'Data export'
                    },
                    {
                        extend:'print',
                    }
                ],

                "paging":true,
                "destroy":true,
                "searching":false,
                "oLanguage": {
                    "aria": {
                        "sortAscending": ": activate to sort column ascending",
                        "sortDescending": ": activate to sort column descending"
                    },
                    "sProcessing":   "处理中...",
                    "sLengthMenu":   "_MENU_ 记录/页",
                    "sZeroRecords":  "没有匹配的记录",
                    "sInfo":         "显示第 _START_ 至 _END_ 项记录，共 _TOTAL_ 项",
                    "sInfoEmpty":    "显示第 0 至 0 项记录，共 0 项",
                    "sInfoFiltered": "(由 _MAX_ 项记录过滤)",
                    "sInfoPostFix":  "",
                    "sSearch":       "过滤:",
                    "oPaginate": {
                        "sFirst":    "首页",
                        "sPrevious": "上页",
                        "sNext":     "下页",
                        "sLast":     "末页"
                    }
                },
                buttons: [
                    {
                        extend: 'excel',//使用 excel扩展
                        text: '导出本页',// 显示文字
                        exportOptions: {
                        }
                    }
                ],

                // Internationalisation. For more info refer to http://datatables.net/manual/i18n
                "language": {
                    "aria": {
                        "sortAscending": ": activate to sort column ascending",
                        "sortDescending": ": activate to sort column descending"
                    },
                    "emptyTable": "No data available in table",
                    "info": "Showing _START_ to _END_ of _TOTAL_ entries",
                    "infoEmpty": "No entries found",
                    "infoFiltered": "(filtered1 from _MAX_ total entries)",
                    "lengthMenu": "Show _MENU_ entries",
                    "search": "Search:",
                    "zeroRecords": "No matching records found"
                },

                // Or you can use remote translation file
                //"language": {
                //   url: '//cdn.datatables.net/plug-ins/3cfcc339e89/i18n/Portuguese.json'
                //},

                "order": [
                    [0, 'asc']
                ],

                "lengthMenu": [
                    [5, 10, 15, 20, -1],
                    [5, 10, 15, 20, "All"] // change per page values here
                ],
                // set the initial value
                "pageLength": 10,

                "dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r><'table-scrollable't><'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>", // horizobtal scrollable datatable

                // Uncomment below line("dom" parameter) to fix the dropdown overflow issue in the datatable cells. The default datatable layout
                // setup uses scrollable div(table-scrollable) with overflow:auto to enable vertical scroll(see: assets/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js).
                // So when dropdowns used the scrollable div should be removed.
                //"dom": "<'row' <'col-md-12'T>><'row'<'col-md-6 col-sm-12'l><'col-md-6 col-sm-12'f>r>t<'row'<'col-md-5 col-sm-12'i><'col-md-7 col-sm-12'p>>",

                "tableTools": {
                    "sSwfPath": "../../assets/global/plugins/datatables/extensions/TableTools/swf/copy_csv_xls_pdf.swf",
                    "aButtons": [{
                        "sExtends": "pdf",
                        "sButtonText": "PDF"
                    }, {
                        "sExtends": "csv",
                        "sButtonText": "CSV"
                    }, {
                        "sExtends": "xls",
                        "sButtonText": "Excel"
                    }, {
                        "sExtends": "print",
                        "sButtonText": "Print",
                        "sInfo": 'Please press "CTR+P" to print or "ESC" to quit',
                        "sMessage": "Generated by DataTables"
                    }]
                },
                ajax:{
                    url:"/UserInfo",
                    data:searchdata,
                    type: "POST"
                },
                "aoColumns": [{
                    "mRender": function(data, type, full) {
                        return full.user_name;
                    }
                },{
                    "mRender": function(data, type, full) {
                        return full.nickname;
                    }
                },{
                    "mRender": function(data, type, full) {

                        return full.occupation;
                    }
                },{
                    "mRender": function(data, type, full) {

                        return full.mobile_number;
                    }
                },{
                    "mRender": function(data, type, full) {

                        return full.email;
                    }
                },{
                    "mRender": function(data, type, full) {
                        return full.create_time;
                    }
                },{
                    "mRender": function(data, type, full) {
                        return "<input type='hidden' name='uid' value='"+ full.user_id+"'><a class=\"edit\" href=\"javascript:;\">Edit </a>";
                    }
                },{
                    "mRender": function(data, type, full) {
                        return "<input type='hidden' name='uid' value='"+ full.user_id+"'><a class=\"delete\" href=\"javascript:;\">Delete </a>";
                    }
                }],

            })

            $("#search_div").modal("hide")
        })
    }

    return {

        //main function to initiate the module
        init: function () {

            if (!jQuery().dataTable) {
                return;
            }
            initTable1();
        }

    };

}();