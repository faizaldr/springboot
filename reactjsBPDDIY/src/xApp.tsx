import React, { lazy, Suspense } from "react";
import type { Product } from "./types/Product";
import { PRODUCTS } from "./data/ProductData";
// import ProductDetailPage from "./pages/ProductDetailPage";
const ProductDetailPage = lazy(() => import("./pages/ProductDetailPage") as Promise<{ default: React.ComponentType<{ product: Product }> }>)

const App: React.FC = () => {
  return (
    <div>
      <Suspense fallback={<div>Loading data</div>}>

        {
          PRODUCTS.map(product => (
            <ProductDetailPage product={product}></ProductDetailPage>
          ))
        }
      </Suspense>

    </div>
  );
};

export default App;
