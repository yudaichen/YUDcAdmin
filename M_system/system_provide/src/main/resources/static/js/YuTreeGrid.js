/**
 *
 * @type 初始化Table
 */
var $table=$('#table');
$(function () {
    console.log("开始执行创建treetable");

    $.ajax({
        url: '/system/sysmenu/bootStrapTableTreeNewUserShow',
        type:"post",
        contentType: "application/json;charsetset=UTF-8",//必须
        dataType: "json",//必须
        success: function (data) {
            console.log("数据也加载了1111")
            createTreeTable(data);
            console.log(data)
        }
    })

    function createTreeTable(dataTreeTable) {
        $table.bootstrapTable({
            data: dataTreeTable,
            idField: 'menuId',
            dataType: 'jsonp',
            columns: [{
                field: 'menuId',
                title: 'ID',
                visible: false
            }, {
                title: '菜单名称',
                field: 'menuName',
                width: '7%',
                formatter: function (value, row, index) {
                    if (row.icon === "" || row.icon === undefined) {
                        return row.menuName;
                    } else {
                        return '<span class="nav-label"><i class="' + row.icon + '"></i> ' + row.menuName + '</span>';
                    }
                }
            }, {
                field: 'orderNum',
                title: '显示顺序',
                visible: false
            }, {
                field: 'parentId',
                title: '父级菜单',
                visible: false
            }, {
                field: 'url',
                width: '15%',
                title: '请求地址',
                align: "center",
            }, {
                title: '类型',
                field: 'menuType',
                width: '10%',
                align: "center",
                formatter: function (value, item, index) {
                    if (item.menuType == 'M') {
                        return '<span class="label label-success">目录</span>';
                    } else if (item.menuType == 'C') {
                        return '<span class="label label-primary">菜单</span>';
                    } else if (item.menuType == 'F') {
                        return '<span class="label label-warning">按钮</span>';
                    }
                }
            }, {
                field: 'visible',
                title: '可见',
                width: '10%',
                align: "center",
                formatter: function (value, row, index) {
                    if (row.visible === '0')
                        return '<span class="label label-success arrowed">显示</span>';
                    else
                        return '';
                }
            }, {
                field: 'perms',
                title: '权限标识',
                width: '8%',
                align: "center"
            }, {
                width: '20%',
                title: '操作',
                align: "center",
                formatter: function (value, row, index) {
                    return '<button type="button" class="RoleOfedit btn-small btn-primary" style="margin-right:15px;"><i class="fa fa-pencil-square-o" ></i>&nbsp;修改</button>' +
                        '<button type="button" class="RoleOfdelete btn-small btn-primary" style="margin-right:15px;"><i class="fa fa-trash-o" ></i>&nbsp;删除</button>';

                }
            },],

            // bootstrap-table-treegrid.js 插件配置 -- start

            //在哪一列展开树形
            treeShowField: 'menuName',
            //指定父id列
            parentIdField: 'parentId',

            onResetView: function (data) {
                //console.log('load');
                $table.treegrid({
                    initialState: 'collapsed',// 所有节点都折叠
                   // initialState: 'expanded',// 所有节点都展开，默认展开
                    treeColumn: 0,
                     expanderExpandedClass: 'glyphicon glyphicon-minus',  //图标样式
                     expanderCollapsedClass: 'glyphicon glyphicon-plus',
                    onChange: function () {
                     //调整页眉和页脚的大小以适合当前列的宽度   $table.bootstrapTable('resetWidth');
                    }
                });

                //只展开树形的第一级节点
                $table.treegrid('getRootNodes').treegrid('expand');

            },
            onCheck: function (row) {
                var datas = $table.bootstrapTable('getData');
                // 勾选子类
                selectChilds(datas, row, "id", "pid", true);

                // 勾选父类
                selectParentChecked(datas, row, "id", "pid")

                // 刷新数据
                $table.bootstrapTable('load', datas);
            },

            onUncheck: function (row) {
                var datas = $table.bootstrapTable('getData');
                selectChilds(datas, row, "id", "pid", false);
                $table.bootstrapTable('load', datas);
            },
            // bootstrap-table-treetreegrid.js 插件配置 -- end
        });
    }


})



/**
 * 选中父项时，同时选中子项
 * @param datas 所有的数据
 * @param row 当前数据
 * @param id id 字段名
 * @param pid 父id字段名
 */
function selectChilds(datas,row,id,pid,checked) {
    for(var i in datas){
        if(datas[i][pid] == row[id]){
            datas[i].check=checked;
            selectChilds(datas,datas[i],id,pid,checked);
        };
    }
}

function selectParentChecked(datas,row,id,pid){
    for(var i in datas){
        if(datas[i][id] == row[pid]){
            datas[i].check=true;
            selectParentChecked(datas,datas[i],id,pid);
        };
    }
}

function test() {
    var selRows = $table.bootstrapTable("getSelections");
    if(selRows.length == 0){
        alert("请至少选择一行");
        return;
    }

    var postData = "";
    $.each(selRows,function(i) {
        postData +=  this.id;
        if (i < selRows.length - 1) {
            postData += "， ";
        }
    });
    alert("你选中行的 id 为："+postData);

}

function add(id) {
    alert("add 方法 , id = " + id);
}
function del(id) {
    alert("del 方法 , id = " + id);
}
function update(id) {
    alert("update 方法 , id = " + id);
}
