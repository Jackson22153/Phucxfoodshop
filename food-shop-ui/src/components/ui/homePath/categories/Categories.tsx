import { useContext } from "react"
import { Category } from "../../../../model/Type"
import CategoriesDetailbox from "../../../shared/functions/categoriesDetailBox/CategoriesDetailbox"
import CategoriesContext from "../../../contexts/CategoriesContext"

export default function CategoriesComponent(){
    const categories = useContext<Category[]>(CategoriesContext)

    return(
        <>
            {/* <!-- service section --> */}
            <section className="service_section layout_padding ">
                <div className="container">
                    <h2 className="custom_heading">Categories</h2>

                    <ul className="category-detail-boxes-ul">
                        {categories.map((category)=>(
                            <li className="row layout_padding2 border-bottom category-detail-box-li" key={category.categoryName}>
                                <CategoriesDetailbox categoryTitle={category.categoryName}
                                    categoryDetail={category.description}
                                    categoryImageSrc={category.picture}
                                />
                            </li>
                        ))}
                    </ul>

                    {/* {categories.map((category)=>(
                        <div className="row layout_padding2" key={category.categoryName}>
                            <CategoriesDetailbox categoryTitle={category.categoryName}
                                categoryDetail={category.description}
                                categoryImageSrc={category.picture}
                            />
                        </div>
                    ))} */}

                </div>
            </section>

            {/* <!-- end service section --> */}
        </>
    )
}