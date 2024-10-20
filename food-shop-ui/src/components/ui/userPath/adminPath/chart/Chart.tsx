
import { useEffect, useState } from "react";
import { defaults } from "chart.js/auto";
import { 
    getPaymentStatusPercentageByYear, getPaymentYears, 
    getRevenuePerMonthByYear, getTopSellingProductByYear 
} from "../../../../../api/AdminApi";
import { PaymentStatusPercentage, Revenue, SellingProduct } from "../../../../../model/Type";
import { displayProductImage } from "../../../../../service/Image";
import ScrollToTop from "../../../../shared/functions/scroll-to-top/ScrollToTop";
import MonthlyRevenueLineChart from "./LineChart";
import MonthlyRevenueDonutChart from "./DonutChart";
import PaymentPercentageBarChar from "./BarChart";

defaults.maintainAspectRatio = false;
defaults.responsive = true;

defaults.plugins.title.display = true;
defaults.plugins.title.align = "start";
defaults.plugins.title.font = {size: 20}
defaults.plugins.title.color = "black";

export default function ChartComponent(){
    const [years, setYears] = useState<number[]>([])
    const [revenueData, setRevenueData] = useState<Revenue[]>([])
    const [products, setProducts] = useState<SellingProduct[]>([])
    const [paymentStatusPercentage, setPaymentStatusPercentage] = useState<PaymentStatusPercentage[]>([])
    const [selectYear, setSelectYear] = useState<number>()

    useEffect(()=>{
        fetchPaymentYears();
    }, [])

      
    // fetch data
    const fetchRevenue = async (year)=>{
        const res = await getRevenuePerMonthByYear(year);
        if(res.status){
            const data = res.data;
            setRevenueData(data)
        }
    }

    const fetchTopSellingProducts = async (year)=>{
        const res = await getTopSellingProductByYear(year);
        if(res.status){
            const data = res.data;
            setProducts(data)
        }
    }

    const fetchPaymentStatusPercentage = async (year)=>{
        const res = await getPaymentStatusPercentageByYear(year);
        if(res.status){
            const data = res.data;
            setPaymentStatusPercentage(data)
        }
    }

    const fetchPaymentYears = async ()=>{
        const res = await getPaymentYears();
        if(res.status){
            const data = res.data;
            const currentYear = data[0]
            setSelectYear(currentYear)
            setYears(data)
            setSelectYear(currentYear)
            fetchRevenue(currentYear);
            fetchTopSellingProducts(currentYear);
            fetchPaymentStatusPercentage(currentYear);
            
        }
    }

    const [currentPage, setCurrentPage] = useState(1);
    const productsPerPage = 2;

    // Calculate the index of the first and last product on the current page
    const indexOfLastProduct = currentPage * productsPerPage;
    const indexOfFirstProduct = indexOfLastProduct - productsPerPage;

    // Slice the products array to get the products for the current page
    const currentProducts = products.slice(indexOfFirstProduct, indexOfLastProduct);

    // Calculate total pages
    const totalPages = Math.ceil(products.length / productsPerPage);

    // Function to handle page change
    const handlePageChange = (pageNumber) => {
        setCurrentPage(pageNumber);
    };
    // Function to go to the next page
    const handleNextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
        }
    };

    // Function to go to the previous page
    const handlePreviousPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    const onChangeSelectYear = (e)=>{
        e.preventDefault();
        const value = e.target.value;
        setSelectYear(value)
        setSelectYear(value)
        fetchRevenue(value);
        fetchTopSellingProducts(value);
        fetchPaymentStatusPercentage(value);
    }


    return(
        <div>
            <ScrollToTop/>
            <div className="container-fluid">
                <h1 className="h3 mb-2 text-gray-800">Charts</h1>
                <div className="row form-group container mb-2">
                    <label className="col-form-label col-md-1" htmlFor="selectYear">Year:</label>
                    <div className="col-md-2">
                        <select className="form-select" id="selectYear" name="year" value={selectYear} 
                            onChange={onChangeSelectYear}>
                            {years && years.length ?
                                years.map((year, index)=>(
                                    <option value={year} key={index}>
                                        {year}
                                    </option>
                                )):
                                <option>There is no data available</option>

                            }
                        </select>
                    </div>
                </div>
                <div className="row">
                    <div className="col-xl-8 col-lg-7">
                        <div className="card shadow mb-4">
                            <div className="card-header py-3">
                                <h6 className="m-0 font-weight-bold text-primary">Line Chart</h6>
                            </div>
                            <div className="card-body">
                                <MonthlyRevenueLineChart revenue={revenueData}/>
                            </div>
                        </div>

                        <div className="card shadow mb-4">
                            <div className="card-header py-3">
                                <h6 className="m-0 font-weight-bold text-primary">Bar Chart</h6>
                            </div>
                            <div className="card-body">
                                <PaymentPercentageBarChar revenue={revenueData}/>
                            </div>
                        </div>

                    </div>

                    <div className="col-xl-4 col-lg-5">
                        <div className="card shadow mb-4">
                            <div className="card-header py-3">
                                <h6 className="m-0 font-weight-bold text-primary">Donut Chart</h6>
                            </div>
                            <div className="card-body">
                                <MonthlyRevenueDonutChart paymentStatusPercentage={paymentStatusPercentage}/>
                            </div>
                        </div>

                        <div className="card shadow mb-4">
                            <div className="card-header py-3">
                                <h6 className="m-0 font-weight-bold text-primary">Top selling Products</h6>
                            </div>
                            <div className="card-body">
                                {currentProducts.length > 0 ? (
                                    <div className="top-product">
                                    {currentProducts.map((product) => (
                                        <div key={product.productID} className="product-card">
                                        <img
                                            className="product-image rounded-3"
                                            src={displayProductImage(product.picture)}
                                            alt="Product Image"
                                        />
                                        <div className="product-details">
                                            <h2 className="product-title">{product.productName}</h2>
                                            <div className="product-rating">
                                            <p>Quantity: {product.quantity}</p>
                                            </div>
                                            <p className="product-price">${product.unitPrice}</p>
                                        </div>
                                        </div>
                                    ))}
                                    </div>
                                ) : (
                                    <p>No products found.</p>
                                )}

                                {/* Pagination Controls */}
                                <div className="Page navigation float-end">
                                    <ul className="pagination">
                                        <li className="page-item">
                                            <button className="rounded-0 page-link" aria-label="Previous"
                                                onClick={()=> handlePreviousPage()}>
                                                <span aria-hidden="true">&laquo;</span>
                                                <span className="sr-only">Previous</span>
                                            </button>
                                        </li>
                                        {Array.from({ length: totalPages }, (_, index) => (
                                            <li className="page-item" key={index}>
                                                <button
                                                    className={`page-link rounded-0 ${currentPage === index + 1?'active':''}`}
                                                    key={index + 1}
                                                    onClick={() => handlePageChange(index + 1)}
                                                    disabled={currentPage === index + 1}
                                                >
                                                    {index + 1}
                                                </button>

                                            </li>
                                        ))}
                                        <li className="page-item">
                                            <button className="rounded-0 page-link" aria-label="Next"
                                                onClick={()=> handleNextPage()}>
                                                <span aria-hidden="true">&raquo;</span>
                                                <span className="sr-only">Next</span>
                                            </button>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    )
}