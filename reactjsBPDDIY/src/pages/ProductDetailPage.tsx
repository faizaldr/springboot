import type { Product } from "../types/Product";

export default function ProductDetailPage({ product }: { product: Product }) {
    return (
        <div>
            <p>{product.id}</p>
            <p>{product.name}</p>
            <p>{product.category}</p>
            <p>{product.price}</p>
        </div>
    )
}