package se.rampoon.example5.beans;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import se.rampoon.cameltest.customerorder.*;
import se.rampoon.example5.utils.JDBCConnectionPool;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * A first very simple bean for persisting orders from messages in a database
 *
 */
@Component
public class DatabaseUtilBean {

    private static final Logger LOG = LoggerFactory.getLogger(DatabaseUtilBean.class);

    @Autowired
    JDBCConnectionPool jdbcConnectionPool;

    /**
     * Persist Orders in database
     * @throws SQLException
     */
    public void saveCustomerOrder(Customerorders  customerorders) throws SQLException      {
        List<CustomerorderType> customerOrderList = customerorders.getCustomerorder();

        customerOrderList.forEach(customerOrder-> {
            CustomerType customerType = customerOrder.getCustomer();
            LOG.info("DatabaseUtilBean.saveCustomerOrder: CustomerId=" + customerType.getId());
            try {
                persistCustomer(customerType);
            } catch (SQLException e) {
                LOG.error("DatabaseUtilBean.saveCustomerOrder: FAILED saving customerType.id=" + customerType.getId() );
               // throw new RuntimeException(e);
            }

            List<Order> orderList = customerOrder.getOrders().getOrder();

            orderList.forEach( order->{
                LOG.info("DatabaseUtilBean.saveCustomerOrder: order.id=" + order.getId() );
                try {
                    persistsOrder(order);
                } catch (SQLException e) {
                    LOG.error("DatabaseUtilBean.saveCustomerOrder: FAILED saving order.id=" + order.getId() );
                    //throw new RuntimeException(e);
                }

            });
        });
    }

    public void persistCustomer( CustomerType customerType) throws SQLException{
        PreparedStatement ps=null;
        Connection con=null;
        try {
            con = jdbcConnectionPool.getConnection();

            String sql = "insert into pm_kund (id,name) values (?,?)";

            LOG.info("DatabaseUtilBean.persistCustomer: kund.id=" + customerType.getId() );

            ps = con.prepareStatement(sql);
            ps.setLong(1,customerType.getId().longValue());
            ps.setString(2,customerType.getName());
            ps.executeUpdate();
        }
        finally {
            if(ps != null) {
                ps.close();
            }
            if(con != null) {
                jdbcConnectionPool.returnConnection(con);
                // con.close();
            }
        }
    }

    public void persistsOrder(Order order) throws SQLException {
        PreparedStatement ps=null;
        Connection con=null;
        try {
            con = jdbcConnectionPool.getConnection();

            String sql = "insert into pm_order (id,date,productName,quantity) values (?,?,?,?)";

            System.out.println("persistOrder starts");

            ps = con.prepareStatement(sql);
            ps.setLong(1,order.getId().longValue());
            Date orderDate = null;
            XMLGregorianCalendar xmlGregorianCalendar = order.getDate();
            if( xmlGregorianCalendar != null ) {
                orderDate = new Date(xmlGregorianCalendar.toGregorianCalendar().getTime().getTime());
            }
            ps.setDate(2, orderDate);
            ps.setString(3,order.getProductName());
            ps.setLong(4,order.getQuantity().longValue());
            ps.executeUpdate();

        }
        finally {
            if(ps != null) {
                ps.close();
            }
            if(con != null) {
                jdbcConnectionPool.returnConnection(con);
                // con.close();
            }
        }
    }
}

