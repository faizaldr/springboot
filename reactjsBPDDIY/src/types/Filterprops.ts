interface FilterProps {
    categories: string[];
    selectedCategory: string;
    onCategoryChange: (category: string) => void;
}