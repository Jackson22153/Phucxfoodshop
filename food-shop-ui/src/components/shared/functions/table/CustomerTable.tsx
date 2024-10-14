import { Link } from "react-router-dom";
import { UserAccount, Pageable } from "../../../../model/Type";
import { displayUserImage } from "../../../../service/Image";

interface Props{
    customers: UserAccount[],
    pageable: Pageable,
    path: string,
}
export default function CustomerTable(prop: Props){
    const customers = prop.customers;
    const path = prop.path;

    return(
        <div className="container bootstrap snippets bootdey border">
            <div className="row">
                <div className="col-lg-12 p-0">
                    <div className="main-box no-header clearfix mb-0">
                        <div className="main-box-body clearfix">
                            <div className="table-responsive">
                                <table className="table user-list">
                                    <thead>
                                        <tr>
                                            <th><span>User</span></th>
                                            <th><span>Email</span></th>
                                            <th><span>First Name</span></th>
                                            <th><span>Last Name</span></th>
                                            <th className="text-center"><span>Email Verified</span></th>
                                            <th className="text-center"><span>Status</span></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {customers.map((customer)=>(
                                            <tr key={customer.userID}>
                                                <td>
                                                    <img src={displayUserImage(customer.picture)} alt="" className="rounded-2"/>
                                                    <Link to={`${path}/${customer.userID}`} className="user-link">
                                                        {customer.username}
                                                    </Link>
                                                    <span className="user-subhead">Customer</span>
                                                </td>
                                                {/* <td>2013/08/12</td>
                                                <td className="text-center">
                                                    <span className="label label-default">pending</span>
                                                </td> */}
                                                <td>
                                                    <Link to={`${path}/${customer.userID}`}>
                                                        {customer.email}
                                                    </Link>
                                                </td>
                                                <td>
                                                    <Link to={`${path}/${customer.userID}`}>
                                                        {customer.firstName}
                                                    </Link>
                                                </td>
                                                <td>
                                                    <Link to={`${path}/${customer.userID}`}>
                                                        {customer.lastName}
                                                    </Link>
                                                </td>
                                                <td className="text-align-center">
                                                    <Link to={`${path}/${customer.userID}`}>
                                                        {customer.emailVerified?"true": "false"}
                                                    </Link>
                                                </td>
                                                <td className="text-align-center">
                                                    <Link to={`${path}/${customer.userID}`}>
                                                        {customer.enabled?"true": "false"}
                                                    </Link>
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}