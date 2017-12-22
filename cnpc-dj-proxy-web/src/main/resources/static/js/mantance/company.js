$(function(){
	

	
	$('#addBtn_c').click(function () {
		
		 $("#fm_c").form('clear');
		 
		$('#dlg_c').dialog({
		    title: '添加厂商',
		    width: 400,
		    height: 300,
		    closed: false,
		    cache: false,
		    href: '',
		    modal: true,
		    buttons:[{
				text:'Save',
				handler:function(){
			        $.ajax({
			            url: ctxPath + "/company/save",
			            data: $('#fm_c').serialize(),
			            type: "post",
			            dataType: "json",
			            success: function (data) {
			            	
			            	 $("#fm_c").form('clear');
			            	 $('#dlg_c').dialog('close');
			            	 $('#dg_c').datagrid('reload');//刷新
			            	 $('#dg_c_off').datagrid('reload');//刷新
			            	 $('#qysjzt').combobox('reload', ctxPath + "/company/load");
			            	 $('#cc').combobox('reload', ctxPath + "/company/load");
			            	 
			            } ,
			            error: function (XMLHttpRequest, textStatus, errorThrown) {
			              
			            },
			 
			        });
			        
				}
			},{
				text:'Close',
				handler:function(){
					$('#dlg_c').dialog('close');
				}
			}]
		});
	});
});



var company = {
		
		
	init : function() {
		$('#dg_c').datagrid({
		    url:ctxPath + "/company/load",
		    columns:[[
				{field:'name',title:'名称',width:100},
			
				{field:'delFlag',title:'状态',width:100,formatter:function(value,rec,index){
    				  if(value == '0'){
	    					  return "正常";
	    				  }else if(value == '1'){
	    					  return "停止";
	    				  }else{
	    					  return "未知";
	    				  }
	    		}},
				
			    {field:'opt',title:'操作',width:100,align:'center',  
                    formatter:function(value,rec,index){
                        var c = '';
                          if(rec.delFlag == '0'){
                       	 c = '<a href="#" mce_href="#" onclick="optCompany(\''+ rec.id + '\',1)">停止</a> '; 
                          }else if(rec.delFlag == '1'){
                       	 c = '<a href="#" mce_href="#" onclick="optCompany(\''+ rec.id + '\',0)">开启</a> '; 
                          }
                       	
                          
                        var e = '<a href="#" mce_href="#" onclick="editCompany(\''+ rec.id + '\')">编辑</a> ';  
                        var d = '<a href="#" mce_href="#" onclick="optCompany(\''+ rec.id + '\',9)">注销</a> ';  
                        return c+e+d;  
                    }  
                  }  
		    ]],
		});
		
		$('#dg_c_off').datagrid({
		    url:ctxPath + "/company/load?delFlag=9",
		    columns:[[
				{field:'name',title:'名称',width:100},
//			
//				{field:'delFlag',title:'状态',width:100,formatter:function(value,rec,index){
//    				  if(value == '0'){
//	    					  return "正常";
//	    				  }else if(value == '1'){
//	    					  return "停止";
//	    				  }else{
//	    					  return "未知";
//	    				  }
//	    		}},
				
			    {field:'opt',title:'操作',width:100,align:'center',  
                    formatter:function(value,rec,index){

                       	
                          
                        var e = '<a href="#" mce_href="#" onclick="optCompany(\''+ rec.id + '\',1)">恢复</a> ';  
                        var d = '<a href="#" mce_href="#" onclick="deleteCompany(\''+ rec.id +'\')">删除</a> ';  
                        return e+d;  
                    }  
                  }  
		    ]],
		});
		
	}



};
function editCompany(id) {
	
    $.ajax({
        url: ctxPath + "/company/get?id="+id,
     
        type: "post",
        dataType: "json",
        success: function (data) {
        	
        	$("#fm_c").form('clear');
        	$('#fm_c').form('load', data);
        
        } ,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
          
        },

    });
    
    
    loadData_c();
  //  return '<a href="javascript:fOpenRoomDetail(\'' + rowIndex + '\',\'' + rowData.id + '\',\'' + rowData.key + '\',\'' + rowData.status + '\')">编辑</a>';
}

function loadData_c(){
	$('#dlg_c').dialog({
	    title: '添加接口',
	    width: 400,
	    height: 200,
	    closed: false,
	    cache: false,
	    href: '',
	    modal: true,
	    buttons:[{
			text:'Save',
			handler:function(){
		        $.ajax({
		            url: ctxPath + "/company/update",
		            data: $('#fm_c').serialize(),
		            type: "post",
		            dataType: "json",
		            success: function (data) {
		            	
		            	 $("#fm_c").form('clear');
		            	 $('#dlg_c').dialog('close');
		            	 $('#dg_c').datagrid('reload');//刷新
		            } ,
		            error: function (XMLHttpRequest, textStatus, errorThrown) {
		              
		            },
		 
		        });
		        
			}
		},{
			text:'Close',
			handler:function(){
				$('#dlg_c').dialog('close');
			}
		}]
	});
	
	

	//$('#dlg').show();
}

function optCompany(id,flg){
	
    $.ajax({  
    	
   	    url: ctxPath + "/company/updateStatus?id="+id+"&delFlag="+flg,
        success:function(){
       	
       	  $('#dg_c').datagrid('reload');//刷新
    	  $('#dg').datagrid('reload');//刷新
    	  $('#dg_off').datagrid('reload');//刷新
    	  $('#dg_c_off').datagrid('reload');//刷新
        }  
    });  
}


function deleteCompany(id) {
	 $.messager.confirm('确认','确认删除?',function(row){  
         if(row){  
             $.ajax({  
            	
            	 url: ctxPath + "/company/ajaxDelete?id="+id,
                 success:function(){
                	
                	  $('#dg_c').datagrid('reload');//刷新
                 }  
             });  
         }  
     })  
}
