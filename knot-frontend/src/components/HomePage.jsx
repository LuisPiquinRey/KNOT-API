import { useEffect,useState } from "react";
import axios from "axios";
export default function HomePage() {
    const[products,setProducts]=useState([]);
    const[currentPage,setCurrentPage]=useState(0);
    const limit=5;
    useEffect(()=>{
        axios.get(`http://localhost:8080/product/findAllProducts/${currentPage}/${limit}`)
            .then((response)=>{
                setProducts(response.data);
            });
    },[currentPage])
    return (
        <div style={{ padding: "20px" }}>
            <h1>Welcome to the Home Page</h1>
            <p>This is a public page accessible by everyone.</p>
            <ul>
                {products.map((product)=>{
                    return <li key={product.id_Product}>{product.name}</li>
                })}
            </ul>
        </div>
    );
}
