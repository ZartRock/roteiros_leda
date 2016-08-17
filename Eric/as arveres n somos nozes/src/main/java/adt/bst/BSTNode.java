package adt.bst;

import adt.bt.BTNode;

public class BSTNode<T extends Comparable<T>> extends BTNode<T> {

	public BSTNode() {
		super();
	}

	public BSTNode(BSTNode<T> parent) {
		super(null, null, null, parent);
	}

	public void add(T data) {
		if (data != null) {
			if (isEmpty()) {
				setData(data);
				setRight(new BSTNode<T>(this));
				setLeft(new BSTNode<T>(this));
			} else {
				int resultComparator = data.compareTo(getData());
				if (resultComparator > 0) {
					getRight().add(data);
				} else if (resultComparator < 0) {
					getLeft().add(data);
				}
			}
		}
	}

	public int getThisHeight() {
		int underThis = 0;
		if (!isEmpty()) {
			int rightHeight = getRight().getThisHeight();
			int leftHeight = getLeft().getThisHeight();
			underThis = Math.max(leftHeight, rightHeight);
			if (this.parent != null) {
				underThis++;
			}
		}
		return underThis;
	}

	public BSTNode<T> search(T element) {
		BSTNode<T> result;
		if (isEmpty() || getData().equals(element)) {
			result = this;
		} else if (getData().compareTo(element) < 0) {
			result = getRight().search(element);
		} else {
			result = getLeft().search(element);
		}
		return result;
	}

	@Override
	public BSTNode<T> getRight() {
		return (BSTNode<T>) super.getRight();
	}

	@Override
	public BSTNode<T> getLeft() {
		return (BSTNode<T>) super.getLeft();
	}

	public BSTNode<T> maximum() {
		BSTNode<T> result = null;
		if (!isEmpty()) {
			result = this;
		}
		if (result != null && !getRight().isEmpty()) {
			result = getRight().maximum();
		}
		return result;
	}

	public BSTNode<T> minimum() {
		BSTNode<T> result = null;
		if (!isEmpty()) {
			result = this;
		}
		if (result != null && !getLeft().isEmpty()) {
			result = getLeft().minimum();
		}
		return result;
	}

	public void remove(T element) {
		if (!this.isEmpty()) {
			int compareResult = this.data.compareTo(element);
			if (compareResult < 0) {
				this.getRight().remove(element);
			} else if (compareResult > 0) {
				this.getLeft().remove(element);
			} else {
				if (getRight().isEmpty() || getLeft().isEmpty()) {
					removeThis();
				} else {
					BSTNode<T> thisNewValue = getRight().minimum();
					this.data = thisNewValue.getData();
					thisNewValue.remove(thisNewValue.getData());
				}
			}
		}
	}

	private void removeThis() {
		BSTNode<T> toReplace = getRight();
		if (toReplace.isEmpty())
			toReplace = getLeft();
		this.data = toReplace.getData();
		this.left = toReplace.getLeft();
		this.right = toReplace.getRight();
	}

	@Override
	public BSTNode<T> getParent() {
		return (BSTNode<T>) super.getParent();
	}

	public T[] order() {
		T[] array = (T[]) new Comparable[0];
		if (!this.isEmpty()) {
			T[] subLeftArray = getLeft().order();
			T[] subRightArray = getRight().order();
			array = mergeAll(this.data, subLeftArray, subRightArray, true);
		}
		return array;
	}

	public T[] postOrder() {
		T[] array = (T[]) new Comparable[0];
		if (!this.isEmpty()) {
			T[] subLeftArray = getLeft().postOrder();
			T[] subRightArray = getRight().postOrder();
			array = mergeAll(this.data, subRightArray, subLeftArray, false);
		}
		return array;
	}
	
	public T[] preOrder() {
		T[] array = (T[]) new Comparable[0];
		if (!this.isEmpty()) {
			T[] subLeftArray = getLeft().preOrder();
			T[] subRightArray = getRight().preOrder();
			array = mergeAll(this.data, subLeftArray, subRightArray, false);
		}
		return array;
	}

	private T[] mergeAll(T element, T[] array1, T[] array2, boolean elemInMiddle) {
		T[] array = (T[]) new Comparable[1 + array1.length + array2.length];
		int elemPos = 0;
		int start = 1;
		if (elemInMiddle) {
			elemPos = array1.length;
			start = 0;
		}
		array[elemPos] = this.data;
		for (int i = start; i < array1.length + start; i++)
			array[i] = array1[i - start];
		start = array1.length + 1;
		for (int i = start; i < array2.length + start; i++)
			array[i] = array2[i - start];

		return array;
	}

	public BSTNode<T> sucessor(BSTNode<T> from) {
		BSTNode<T> result = null;
		if (!this.isEmpty()) {
			boolean rightIsEligible = !getRight().isEmpty() && !getRight().getData().equals(from.getData());
			if (rightIsEligible) {
				result = getRight().minimum();
			} else if (getParent() != null) {
				int comparation = getParent().getData().compareTo(this.getData());
				if (comparation > 0)
					result = getParent();
				else
					result = getParent().sucessor(this);
			}
		}
		return result;
	}

	public BSTNode<T> predecessor(BSTNode<T> from) {
		BSTNode<T> result = null;
		if (!this.isEmpty()) {
			boolean leftIsEligible = !getLeft().isEmpty() && !getLeft().getData().equals(from.getData());
			if (leftIsEligible) {
				result = getLeft().maximum();
			} else if (getParent() != null) {
				int comparation = getParent().getData().compareTo(this.getData());
				if (comparation < 0)
					result = getParent();
				else
					result = getParent().predecessor(this);
			}
		}
		return result;
	}
}
