package com.quick.modules.system.utils;

import com.quick.common.util.MyConvertUtils;
import com.quick.modules.system.entity.SysDepart;
import com.quick.modules.system.model.DepartIdModel;
import com.quick.modules.system.model.SysDepartTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FindsDepartsChildrenUtil
 * @Description 对应部门的表,处理并查找树级数据
 * @Author zhoujian
 * @Time 2022/11/28 23:25
 * @Version 1.0
 */
public class FindsDepartsChildrenUtil {


    /**
     * queryTreeList的子方法 ====1=====
     * 该方法是s将SysDepart类型的list集合转换成SysDepartTreeModel类型的集合
     */
    public static List<SysDepartTreeModel> wrapTreeDataToTreeList(List<SysDepart> recordList) {

        List<DepartIdModel> idList = new ArrayList<DepartIdModel>();
        List<SysDepartTreeModel> records = new ArrayList<>();
        for (SysDepart sysDepart : recordList) {
            records.add(new SysDepartTreeModel(sysDepart));
        }

        List<SysDepartTreeModel> tree = findChildren(records, idList);
        setEmptyChildrenAsNull(tree);
        return tree;
    }

    /**
     * queryTreeList的子方法 ====4====
     * 该方法是将子节点为空的List集合设置为Null值
     */
    private static void setEmptyChildrenAsNull(List<SysDepartTreeModel> treeList) {
        for (int i = 0; i < treeList.size(); i++) {
            SysDepartTreeModel model = treeList.get(i);
            if (model.getChildren().size() == 0) {
                model.setChildren(null);
                model.setIsLeaf(true);
            }else{
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(false);
            }
        }
    }

    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<SysDepartTreeModel> findChildren(List<SysDepartTreeModel> records, List<DepartIdModel> idList) {
        List<SysDepartTreeModel> treeList = new ArrayList<>();
        for (SysDepartTreeModel record : records) {
            if (MyConvertUtils.isEmpty(record.getParentId())){
                treeList.add(record);
                DepartIdModel departIdModel = new DepartIdModel().convert(record);
                idList.add(departIdModel);
            }
        }
        getGrandChildren(treeList,records,idList);
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<SysDepartTreeModel> treeList, List<SysDepartTreeModel> records, List<DepartIdModel> idList) {
        for (int i = 0; i < treeList.size(); i++) {
            SysDepartTreeModel topModel = treeList.get(i);
            DepartIdModel topIdModel = idList.get(i);
            for (SysDepartTreeModel  model: records) {
                if (model.getParentId() != null && model.getParentId().equals(topModel.getId())){
                    topModel.getChildren().add(model);
                    DepartIdModel departIdModel = new DepartIdModel().convert(model);
                    topIdModel.getChildren().add(departIdModel);
                }
            }
            getGrandChildren(topModel.getChildren(), records, topIdModel.getChildren());
        }
    }
}
