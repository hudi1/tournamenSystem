package org.tahom.web.components;

import java.util.List;

import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.navigation.paging.IPageableItems;
import org.apache.wicket.model.IModel;

public abstract class PropertyPageableListView<T> extends PropertyListView<T> implements IPageableItems {
	private static final long serialVersionUID = 1L;

	/** The page to show. */
	private long currentPage;

	/** Number of rows per page of the list view. */
	private long itemsPerPage;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            See Component
	 * @param model
	 *            See Component
	 * @param itemsPerPage
	 *            Number of rows to show on a page
	 */
	public PropertyPageableListView(final String id, int itemsPerPage) {
		super(id);
		setItemsPerPage(itemsPerPage);
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 *            See Component
	 * @param model
	 *            See Component
	 * @param itemsPerPage
	 *            Number of rows to show on a page
	 */
	public PropertyPageableListView(final String id, final IModel<? extends List<T>> model, int itemsPerPage) {
		super(id, model);
		setItemsPerPage(itemsPerPage);
		setReuseItems(true);
	}

	/**
	 * Creates a pageable list view having the given number of rows per page that uses the provided object as a simple
	 * model.
	 * 
	 * @param id
	 *            See Component
	 * @param list
	 *            See Component
	 * @param itemsPerPage
	 *            Number of rows to show on a page
	 * @see ListView#ListView(String, List)
	 */
	public PropertyPageableListView(final String id, final List<T> list, final int itemsPerPage) {
		super(id, list);
		this.itemsPerPage = itemsPerPage;
		setReuseItems(true);
	}

	/**
	 * Gets the index of the current page being displayed by this list view.
	 * 
	 * @return Returns the currentPage.
	 */
	public final long getCurrentPage() {
		// If first cell is out of range, bring page back into range
		while ((currentPage > 0) && ((currentPage * itemsPerPage) >= getItemCount())) {
			currentPage--;
		}

		return currentPage;
	}

	/**
	 * Gets the number of pages in this list view.
	 * 
	 * @return The number of pages in this list view
	 */
	public final long getPageCount() {
		return ((getItemCount() + itemsPerPage) - 1) / itemsPerPage;
	}

	/**
	 * Gets the maximum number of rows on each page.
	 * 
	 * @return the maximum number of rows on each page.
	 */
	public final long getItemsPerPage() {
		return itemsPerPage;
	}

	/**
	 * Sets the maximum number of rows on each page.
	 * 
	 * @param itemsPerPage
	 *            the maximum number of rows on each page.
	 */
	public final void setItemsPerPage(long itemsPerPage) {
		if (itemsPerPage < 0) {
			itemsPerPage = 0;
		}

		addStateChange();
		this.itemsPerPage = itemsPerPage;
	}

	/**
	 * Sets the maximum number of rows on each page.
	 * 
	 * @param itemsPerPage
	 *            the maximum number of rows on each page.
	 */
	public final void setItemsPerPage(int itemsPerPage) {
		if (itemsPerPage < 0) {
			itemsPerPage = 0;
		}

		addStateChange();
		this.itemsPerPage = itemsPerPage;
	}

	/**
	 * @return offset of first item
	 */
	public long getFirstItemOffset() {
		return getCurrentPage() * getItemsPerPage();
	}

	/**
	 * @see org.apache.wicket.markup.html.navigation.paging.IPageableItems#getItemCount()
	 */
	public long getItemCount() {
		return getList().size();
	}

	/**
	 * @see org.apache.wicket.markup.html.list.ListView#getViewSize()
	 */
	@Override
	public int getViewSize() {
		if (getDefaultModelObject() != null) {
			super.setStartIndex((int) getFirstItemOffset());
			super.setViewSize((int) getItemsPerPage());
		}

		return super.getViewSize();
	}

	/**
	 * Sets the current page that this list view should show.
	 * 
	 * @param currentPage
	 *            The currentPage to set.
	 */
	public final void setCurrentPage(long currentPage) {
		if (currentPage < 0) {
			currentPage = 0;
		}

		long pageCount = getPageCount();
		if ((currentPage > 0) && (currentPage >= pageCount)) {
			currentPage = pageCount - 1;
		}

		addStateChange();
		this.currentPage = currentPage;
	}

	/**
	 * Prevent users from accidentally using it.
	 * 
	 * @see org.apache.wicket.markup.html.list.ListView#setStartIndex(int)
	 * @throws UnsupportedOperationException
	 *             always
	 */
	@Override
	public ListView<T> setStartIndex(int startIndex) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("You must not use setStartIndex() with PageableListView");
	}

	/**
	 * Prevent users from accidentally using it.
	 * 
	 * @param size
	 *            the view size
	 * @return This
	 * @throws UnsupportedOperationException
	 *             always
	 * @see org.apache.wicket.markup.html.list.ListView#setStartIndex(int)
	 */
	@Override
	public ListView<T> setViewSize(int size) throws UnsupportedOperationException {
		throw new UnsupportedOperationException("You must not use setViewSize() with PageableListView");
	}

}