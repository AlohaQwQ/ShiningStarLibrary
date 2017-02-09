package aloha.shiningstarbase.util.recycleviewadapter;

/**
 * 多种ItemViewType 实现接口，根据Item指定ItemType，并且根据ItemType指定相应的布局文件。
 * 实现接口作为参数传入到MultiItemCommonAdapter。
 *
 * MultiItemTypeSupport  multiItemSupport = new MultiItemTypeSupport<ChatMessage>(){

	 @Override
	 public int getItemViewType(int postion, ChatMessage msg){
	 	//根据当前的bean返回item type
	 }

	@Override
	public int getLayoutId(int itemType){
		//根据itemType返回item布局文件id
	}
   }
 * @param <T>
 */
public interface MultiItemTypeSupport<T> {
	//根据当前的bean返回item type
	int getItemViewType(int position, T t);
	//根据itemType返回item布局文件id
	int getLayoutId(int itemType);
}