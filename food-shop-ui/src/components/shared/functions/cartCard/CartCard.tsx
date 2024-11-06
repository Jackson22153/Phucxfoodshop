import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faMinus, faPlus, faTrash } from "@fortawesome/free-solid-svg-icons";
import { displayProductImage } from "../../../../service/Image";
import { ProductWithDiscount } from "../../../../model/Type";
import { ceilRound } from "../../../../service/Convert";
interface Props{
    number: number,
    product: ProductWithDiscount,
    onChangeQuantity: any,
    onClickIncrementQuantity: any,
    onClickDecrementQuantity: any,
    onClickRemoveProduct: any
}

export default function CartCard(prop: Props){
    const product = prop.product;
    const name = product.productName;
    const category = product.categoryName;
    const imgSrc = product.picture;
    var quantity = product.quantity;
    const productIndex = prop.number;

    const onChangeQuantity = (event: any)=>{
        prop.onChangeQuantity(event, productIndex);
    }

    const onClickIncrement  = (_event: any)=>{
        prop.onClickIncrementQuantity(productIndex);
    }

    const onClickDecrement = (_event: any)=>{
        prop.onClickDecrementQuantity(productIndex);
    }
    
    const onClickRemove = (_event: any)=>{
        prop.onClickRemoveProduct(productIndex)
    }

    function extendedPrice(product: ProductWithDiscount){
        var totalDiscount = 0;
        product.discounts.forEach((discount) => {
            totalDiscount+=discount.discountPercent;
        });
        const discountPercent = (1-totalDiscount/100);
        const price = product.unitPrice*product.quantity*discountPercent
        return ceilRound(price);
    }
    return(
        <>
            <div className="col-md-2 col-lg-2 col-xl-2 col-2 cart-img">
                <img src={displayProductImage(imgSrc)} className="img-fluid rounded-3" alt={name}/>
            </div>
            <div className="col-md-3 col-lg-3 col-xl-3 col-4">
                <h6 className="text-muted">{category}</h6>
                <h6 className="text-black mb-0">{name}</h6>
            </div>
            <div className="col-md-3 col-lg-2 col-xl-2 col-3 d-flex p-0">
                <button className="btn btn-link px-2 decrement-btn" onClick={onClickDecrement}>
                    <FontAwesomeIcon icon={faMinus}/>
                </button>

                <input min="1" name="quantity" value={quantity} type="number"
                    onChange={onChangeQuantity} className="form-control form-control-sm product-quanlity-input" />

                <button className="btn btn-link px-2 increment-btn" onClick={onClickIncrement}>
                    <FontAwesomeIcon icon={faPlus}/>
                </button>
            </div>
            <div className="col-md-2 col-lg-2 col-xl-2 col-2 offset-lg-1">
                <h6 className="mb-0">$ {extendedPrice(product)}</h6>
            </div>
            <div className="col-md-1 col-lg-1 col-xl-1 text-end margin-left-auto">
                <button className="text-muted btn btn-light delete-btn" onClick={onClickRemove}>
                    <FontAwesomeIcon icon={faTrash}/>
                </button>
            </div>
        </>
    );
}