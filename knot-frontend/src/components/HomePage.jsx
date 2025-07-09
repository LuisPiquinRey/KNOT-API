import { useEffect,useState } from "react";
import '../css/HomePage.css'
import axios from "axios";
export default function HomePage() {
    const[products,setProducts]=useState([]);
    const[currentPage,setCurrentPage]=useState(0);
    const limit=6;
    useEffect(()=>{
        axios.get(`http://localhost:8080/product/findAllProducts/${currentPage}/${limit}`)
            .then((response)=>{
                console.log(response.data);
                setProducts(response.data);
            });
    },[currentPage])
    return (
        <div style={{ padding: "20px" }}>
            <h1>Welcome to the Home Page</h1>
            <p>This is a public page accessible by everyone.</p>
            <div className="grid-knot">
                {products.map((product)=>{
                    return<div className="product-knot" key={product.id_Product}>
                        <h2>{product.name}</h2>
                        {product.description && <h2>{product.description}</h2>}
                        <img className="image-product" src={`http://localhost:8080/product/images/${product.id_Product}`}></img>
                    </div>
                })}
            </div>
        </div>
    );
}
