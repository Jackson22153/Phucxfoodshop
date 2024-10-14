import dayjs from "dayjs";
import { OrderWithProduct } from "../../../../model/Type";
import { displayProductImage } from "../../../../service/Image";

interface Prop{
    orderInfo: OrderWithProduct
}
export default function EmployeeOrder(prop: Prop){
    const orderInfo = prop.orderInfo;
    return(
        <div id="order-bill-container">
            {orderInfo &&
                <>
                    <div className="d-flex flex-column justify-content-center align-items-center position-relative pt-3 rounded-top-5 col-sm-12 col-md-10 mx-auto col-lg-7" id="order-heading">
                        <div className="text-uppercase">
                            <p>Order detail</p>
                        </div>
                        <div className="h4">{dayjs(orderInfo.orderDate).toString()}</div>
                        <div className="pt-1">
                            {orderInfo.shippedDate?
                                <p>Order #{orderInfo.orderID} was delivered on <b className="text-dark"> {orderInfo.shippedDate}</b></p>:
                                <p>Order #{orderInfo.orderID} is <b className="text-dark"> {orderInfo.status}</b></p>
                            }
                        </div>
                    </div>
                    <div className="bg-white rounded-bottom-5 px-3 px-md-5 pb-4 col-sm-12 col-md-10 mx-auto col-lg-7">
                        <div className="table-responsive">
                            <table className="table table-borderless">
                                <thead>
                                    <tr className="text-uppercase text-muted">
                                        <th scope="col">product</th>
                                        <th scope="col" className="text-right">total</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <th scope="row">Food</th>
                                        <td className="text-right"><b>${orderInfo.totalPrice}</b></td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                        {orderInfo.products.map(product =>(
                            <div key={product.productID} className="d-flex justify-content-start align-items-center list py-1 px-2">
                                <div><b>{product.quantity}px</b></div>
                                <div className="mx-3">
                                    <img src={displayProductImage(product.picture)}
                                        alt="product" className="rounded-circle" width="30" height="30"/>
                                </div>
                                <div className=""><p className="m-0">{product.productName}</p></div>
                                
                                {product.totalDiscount>0 &&
                                    <div className="text-white d-flex justify-content-center align-items-center small-ele ms-4">
                                        -{product.totalDiscount}%
                                    </div>
                                }

                                <div className="text-right ml-auto">
                                    ${product.extendedPrice}
                                </div>
                            </div>
                        ))}

                        <div className="pt-2 border-bottom mb-3"></div>
                        <div className="d-flex justify-content-start align-items-center pl-3">
                            <div className="text-muted">Payment Method</div>
                            <div className="ml-auto">
                                <div>COD</div>
                                {/* <img src="https://www.freepnglogos.com/uploads/mastercard-png/mastercard-logo-logok-15.png" alt=""
                                    width="30" height="30"/>
                                <label>Mastercard ******5342</label> */}
                            </div>
                        </div>
                        <div className="d-flex justify-content-start align-items-center py-1 pl-3">
                            <div className="text-muted">Shipping</div>
                            <div className="ml-auto">
                                <label>${orderInfo.freight}</label>
                            </div>
                        </div>
                        <div className="d-flex justify-content-start align-items-center pb-4 pl-3 border-bottom">
                            {/* <div className="text-muted">
                                <button className="text-white btn">50% Discount</button>
                            </div>
                            <div className="ml-auto price">
                                -$34.94
                            </div> */}
                        </div>
                        <div className="d-flex justify-content-start align-items-center pl-3 py-3 mb-4 border-bottom">
                            <div className="text-muted">
                                Total
                            </div>
                            <div className="ml-auto h5 font-weight-bold">
                                ${orderInfo.totalPrice + orderInfo.freight}
                            </div>
                        </div>
                        <div className="row border rounded p-1 my-3 d-flex">
                            <div className="col-md-6 py-3">
                                <div className="d-flex flex-column align-items start">
                                    <div>
                                        <b>Employee</b>
                                        <p className="text-justify pt-2">EmployeeID: {orderInfo.employeeID}</p>
                                    </div>
                                    <div className="d-flex justify-content-start align-items-center pb-4 pl-3 border-bottom"/>
                                    <div className= 'py-3'>
                                        <b>Ship</b>
                                        <p className="text-justify pt-2">{orderInfo.shipperName}</p>
                                        {/* <p className='text-justify'>{orderInfo.phone}</p> */}
                                    </div>
                                </div>
                            </div>
                            <div className="col-md-6 py-3">
                                <div className="d-flex flex-column align-items start">
                                    <b>Shipping Address</b>
                                    <p className="text-justify pt-2">{orderInfo.shipName}, {orderInfo.shipAddress}</p>
                                    <p className="text-justify">{orderInfo.shipCity}</p>
                                </div>
                            </div>
                        </div>
                        {/* <div className="pl-3 font-weight-bold">Related Subsriptions</div> */}
                        <div className="d-sm-flex justify-content-between rounded my-3 subscriptions">
                            <div>
                                <b>#{orderInfo.orderID}</b>
                            </div>
                            <div>{dayjs(orderInfo.orderDate).toString()}</div>
                            <div>Status: {orderInfo.status}</div>
                            <div>
                                Total: <b> ${orderInfo.totalPrice}</b>
                            </div>
                        </div>
                    </div>
                </>
            }
        </div>
    );

}