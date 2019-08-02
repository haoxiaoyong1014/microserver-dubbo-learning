package cn.haoxy.micro.server.dubbo.common.req;

/**
 * @author Haoxy
 * Created in 2019-08-02.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * @description 查询请求
 */
public class QueryReq extends AbsReq {

    /** 页码 */
    protected int page = 1;

    /** 每页显示的条数 */
    protected int numPerPage = 10;

    //当前行号
    protected int currentPage;

    public int getCurrentPage() {
        return (page-1)*numPerPage;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
}
