import { getTossPay } from '../payment';

interface Payment {
  paymentKey: string;
  orderId: string;
  amount: string;
}

const getTossPayQuery = (payment: Payment) => {
  return {
    queryKey: ['getTossPay'],
    queryFn: getTossPay(payment),
  };
};

export { getTossPayQuery };
