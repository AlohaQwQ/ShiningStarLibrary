package aloha.shiningstarbase.util.recycleviewadapter.support;

/**
 * 添加分类header头部 实现接口
 * SectionSupport<String> sectionSupport = new SectionSupport<String>(){

     @Override
     public int sectionHeaderLayoutId() {
        return R.layout.header;
     }

     @Override
     public int sectionTitleTextViewId() {
        return R.id.id_header_title;
     }

     @Override
     public String getTitle(String s) {
        return s.substring(0, 1);
     }
   };
 * Created by zhy on 16/4/9.
 * @param <T> 数据类型Bean
 */
public interface SectionSupport<T> {
    public int sectionHeaderLayoutId();

    public int sectionTitleTextViewId();

    public String getTitle(T t);
}
