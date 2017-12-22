$(function(){
	$('#cc').combobox({
	    url:ctxPath + "/company/load",  
	    valueField:'id',
	    textField:'name'
	});
	//服务注销页面select
	$('#companyselect').combobox({
	    url:ctxPath + "/company/load",  
	    valueField:'id',
	    textField:'name',
	    	
	  	  onSelect: function (rec) {
	  		   var optv = rec.id;
	    		$('#dg_off').datagrid({
  	    		    url:ctxPath + "/loadByCompany?companyId="+optv+"&status=9",
  	    		    columns:[[
  	    				{field:'name',title:'名称',width:100},
  	    				{field:'fromUrl',title:'服务',width:110},
  	    				{field:'covertUrl',title:'covertUrl',width:350},
  	    				{field:'paramType',title:'参数类型',width:50,formatter:function(value,rec,index){
    	    				  if(value == '1'){
      	    					  return "get";
      	    				  }else if(value == '2'){
      	    					return "post";
      	    				  }else{
      	    					return "未知类型";
      	    				  }
      	    			}},
  	    				{field:'type',title:'类型',width:50},
  	    			    {field:'opt',title:'操作',width:100,align:'center',  
  	                        formatter:function(value,rec,index){  
  	                         
  	                        	
  	                            var e = '<a href="#" mce_href="#" onclick="optMent(\''+ rec.id + '\',1)">恢复</a> '; 
  	                            var h = '<a href="#" mce_href="#" onclick="deleteMant(\''+ rec.id + '\')">删除</a> ';  
  	                        //    var d = '<a href="#" mce_href="#" onclick="deleteMant(\''+ rec.id +'\')">删除</a> ';  
  	                            return e+h;  
  	                        }  
  	                      }  
  	    		    ]],
  	    		});
	    		

	      },
		 onLoadSuccess: function () {  //加载完成后,设置选中第一项  
	         var val = $(this).combobox("getData");  
	         for (var item in val[0]) {  
	             if (item == "id") {  
	                 $(this).combobox("select", val[0][item]);  
	             }  
	         }  
	     }  
	      
	
	});

	var setting = {
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false
			},
			data: {
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: ""
				}
			},
			callback: {
		
			    onClick:function(event , treeId,treeNode,clickFlag){
		    		$('#dg').datagrid({
      	    		    url:ctxPath + "/loadByCompany?companyId="+treeNode.id,
      	    		    columns:[[
      	    				{field:'name',title:'名称',width:100},
      	    				{field:'fromUrl',title:'服务',width:110},
      	    				{field:'covertUrl',title:'covertUrl',width:350},
      	    				{field:'status',title:'状态',width:100,formatter:function(value,rec,index){
      	    				  if(value == '0'){
      	    					  return "正常";
      	    				  }else{
      	    					return "停止";
      	    				  }
      	    				}},
      	    				{field:'paramType',title:'参数类型',width:50,formatter:function(value,rec,index){
        	    				  if(value == '1'){
          	    					  return "get";
          	    				  }else if(value == '2'){
          	    					return "post";
          	    				  }else{
          	    					return "未知类型";
          	    				  }
          	    			}},
      	    				{field:'type',title:'类型',width:50},
      	    			    {field:'opt',title:'操作',width:100,align:'center',  
      	                        formatter:function(value,rec,index){  
      	                           var c = '';
      	                           if(rec.status == '0'){
      	                        	 c = '<a href="#" mce_href="#" onclick="optMent(\''+ rec.id + '\',1)">停止</a> '; 
      	                           }else if(rec.status == '1'){
      	                        	 c = '<a href="#" mce_href="#" onclick="optMent(\''+ rec.id + '\',0)">开启</a> '; 
      	                           }
      	                        	
      	                        	
      	                            var e = '<a href="#" mce_href="#" onclick="editMant(\''+ rec.id + '\')">编辑</a> ';  
      	                            var h = '<a href="#" mce_href="#" onclick="optMent(\''+ rec.id + '\',9)">注销</a> ';  
      	                        //    var d = '<a href="#" mce_href="#" onclick="deleteMant(\''+ rec.id +'\')">删除</a> ';  
      	                            return c+e+h;  
      	                        }  
      	                      }  
      	    		    ]],
      	    		});
			    }
			}
		};
	
	var t = $("#tree");
	t = $.fn.zTree.init(t, setting, zNodes);
	var nodes = t.getNodes();
    t.selectNode(nodes[0]);
    $('#tree_1_a').trigger('click');
	    
	$('#addBtn').click(function () {
		
		 $("#fm").form('clear');
		 
		$('#dlg').dialog({
		    title: '添加厂商',
		    width: 800,
		    height: 350,
		    closed: false,
		    cache: false,
		    href: '',
		    modal: true,
		    buttons:[{
				text:'Save',
				handler:function(){
			        $.ajax({
			            url: ctxPath + "/save",
			            data: $('#fm').serialize(),
			            type: "post",
			            dataType: "json",
			            success: function (data) {
			            	
			            	 $("#fm").form('clear');
			            	 $('#dlg').dialog('close');
			            	 $('#dg').datagrid('reload');//刷新
			            } ,
			            error: function (XMLHttpRequest, textStatus, errorThrown) {
			              
			            },
			 
			        });
			        
				}
			},{
				text:'Close',
				handler:function(){
					$('#dlg').dialog('close');
				}
			}]
		});
	});
});



var Mant = {
		


};

//修改状态
function optMent(id,flg){
	
    $.ajax({  
    	
   	    url: ctxPath + "/updateStatus?id="+id+"&status="+flg,
        success:function(){
       	
       	  $('#dg').datagrid('reload');//刷新
       	  $('#dg_off').datagrid('reload');//刷新
        }  
    });  
}
//编辑
function editMant(id) {
	
	    
    $.ajax({
        url: ctxPath + "/get?id="+id,
     
        type: "post",
        dataType: "json",
        success: function (data) {
        	
        	$("#fm").form('clear');
        	$('#fm').form('load', data);
        
        } ,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
          
        },

    });
    
    
	loadData();
  //  return '<a href="javascript:fOpenRoomDetail(\'' + rowIndex + '\',\'' + rowData.id + '\',\'' + rowData.key + '\',\'' + rowData.status + '\')">编辑</a>';
}

function loadData(){
	$('#dlg').dialog({
	    title: '添加接口',
	    width: 800,
	    height: 350,
	    closed: false,
	    cache: false,
	    href: '',
	    modal: true,
	    buttons:[{
			text:'Save',
			handler:function(){
		        $.ajax({
		            url: ctxPath + "/update",
		            data: $('#fm').serialize(),
		            type: "post",
		            dataType: "json",
		            success: function (data) {
		            	
		            	 $("#fm").form('clear');
		            	 $('#dlg').dialog('close');
		            	 $('#dg').datagrid('reload');//刷新
		            } ,
		            error: function (XMLHttpRequest, textStatus, errorThrown) {
		              
		            },
		 
		        });
		        
			}
		},{
			text:'Close',
			handler:function(){
				$('#dlg').dialog('close');
			}
		}]
	});
	
}

function deleteMant(id) {
	 $.messager.confirm('确认','确认删除?',function(row){  
         if(row){
             $.ajax({  
            	
            	 url: ctxPath + "/ajaxDelete?id="+id,
                 success:function(){
                	
                	  $('#dg').datagrid('reload');//刷新
                 }  
             });  
         }  
     })  
}
