import { useEffect, useState } from "react";
import Sidebar from "./Sidebar";
import Header from "./Header";
import OverviewCards from "./OverviewCards";
import ExcelUpload from "./ExcelUpload";
import SearchFilter from "./SearchFilter";
import MedicineList from "./MedicineList";
import AddInventoryForm from "./AddInventoryForm";

export default function Dashboard() {
  const [showForm, setShowForm] = useState(false);
  const [medicines, setMedicines] = useState([
    { id: 1, medicine_name: "Paracetamol", brand_name: "Brand A", dosage: "500mg", unitPrice: 10, stock: 50, status: "Available" },
    { id: 2, medicine_name: "Ibuprofen", brand_name: "Brand B", dosage: "400mg", unitPrice: 15, stock: 0, status: "Out of Stock" },
    { id: 3, medicine_name: "Amoxicillin", brand_name: "Brand C", dosage: "250mg", unitPrice: 20, stock: 20, status: "Available" },
  ]);
  const [searchQuery, setSearchQuery] = useState("");
  const [filterStatus, setFilterStatus] = useState("All");

  useEffect(() => {
    const fetchData = async () => {
      const getEmail = localStorage.getItem('email')
      const formData = {
        email : getEmail
      }
      try {
        const response = await fetch("http://localhost:8080/inventory/getByEmail", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify(formData),
        });
  
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
  
        const data = await response.json(); // Parse JSON response
        console.log("Fetched Data:", data);
        setMedicines(data); // Update state with fetched medicines
      } catch (err) {
        console.log("Error: " + err.message);
      }
    };
  
    fetchData();
  }, []);
  

  const handleIncreaseStock = (id) => {
    setMedicines((prev) =>
      prev.map((med) =>
        med.id === id
          ? {
              ...med,
              stock: med.stock + 1,
              status: med.stock + 1 > 0 ? "Available" : "Out of Stock", // Update status based on stock
            }
          : med
      )
    );
  };

  const handleDecreaseStock = (id) => {
    setMedicines((prev) =>
      prev.map((med) =>
        med.id === id
          ? {
              ...med,
              stock: Math.max(0, med.stock - 1),
              status: med.stock - 1 > 0 ? "Available" : "Out of Stock", // Update status based on stock
            }
          : med
      )
    );
  };

  const handleDeleteMedicine = (id) => {
    setMedicines((prev) => prev.filter((med) => med.id !== id));
  };

  return (
    <div className="flex h-screen bg-gradient-to-r from-blue-500 to-indigo-600 relative overflow-hidden">
      <Sidebar setShowForm={setShowForm} />
      <div className="flex-1 p-6 bg-gray-100 z-10 overflow-y-auto">
        <Header />
        <OverviewCards medicines={medicines} />
        <ExcelUpload />
        <SearchFilter
          searchQuery={searchQuery}
          setSearchQuery={setSearchQuery}
          filterStatus={filterStatus}
          setFilterStatus={setFilterStatus}
        />
        <MedicineList
          medicines={medicines}
          searchQuery={searchQuery}
          filterStatus={filterStatus}
          handleIncreaseStock={handleIncreaseStock}
          handleDecreaseStock={handleDecreaseStock}
          handleDeleteMedicine={handleDeleteMedicine}
        />
      </div>
      {showForm && <AddInventoryForm onClose={() => setShowForm(false)} />}
    </div>
  );
}