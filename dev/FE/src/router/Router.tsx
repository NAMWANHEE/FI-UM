import {
  AuctionDetailPage,
  AuctionPage,
  CheckoutPage,
  CreatePage,
  DepositPage,
  FundingPage,
  GalleryDetailPage,
  GalleryPage,
  LoginPage,
  MainPage,
  ProfilePage,
  StockDetailPage,
  StockPage,
  QuizPage,
} from '@/pages';
import EducationPage from '@/pages/EducationPage/EducationPage';
import TossSuccessPage from '@/pages/TossPaymentPage/TossSuccessPage';
import { Route, Routes, Outlet } from 'react-router-dom';

const Router = ({ className }: { className?: string }) => {
  return (
    <div className={className}>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route path="" element={<MainPage />} />
          <Route path="login" element={<LoginPage />} />
          <Route path="signup" element={<LoginPage signUp />} />
          <Route path="stock" element={<StockPage />} />
          <Route path="stock/:detail" element={<StockDetailPage />} />
          <Route path="auction" element={<AuctionPage />} />
          <Route path="auction/:auctionNo" element={<AuctionDetailPage />} />
          <Route path="create" element={<CreatePage />} />
          <Route path="deposit" element={<DepositPage />} />
          <Route path="funding" element={<FundingPage />} />
          <Route path="profile/:userNo" element={<ProfilePage />} />
          <Route path="gallery" element={<GalleryPage />} />
          <Route path="gallery/:detail" element={<GalleryDetailPage />} />
          <Route path="tosspay" element={<CheckoutPage />} />
          <Route path="tosspay/success" element={<TossSuccessPage />} />
          <Route path="education" element={<EducationPage />} />
          <Route path="education/quiz" element={<QuizPage />} />
        </Route>
      </Routes>
    </div>
  );
};

export default Router;

const Layout = () => {
  return (
    <>
      <Outlet />
    </>
  );
};
