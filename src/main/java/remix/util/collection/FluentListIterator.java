package remix.util.collection;

class FluentListIterator implements java.util.Iterator {
    private int a;
    FluentIterableList fluentIterableList;

    @Override
    public Object next() {
        return this.fluentIterableList.method_f2d74290405e3deed647(this.a++);
    }

    @Override
    public void remove() {
        this.fluentIterableList.method_f93cf818ee211f14c02a(this.fluentIterableList.method_f2d74290405e3deed647(this.a));
    }

    @Override
    public boolean hasNext() {
        return this.a < this.fluentIterableList.method_7eec03da329a017ec143()
                && this.fluentIterableList.method_f2d74290405e3deed647(this.a) != null;
    }

    FluentListIterator(FluentIterableList bTbValue) {
        this.fluentIterableList = bTbValue;
        this.a = 0;
    }
}
