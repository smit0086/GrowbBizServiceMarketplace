import { COPY } from "@/lib/constants";
import CustomerLogin from "@/app/login/page"

export default function Home() {
    // return <main>{COPY.APP_NAME}</main>;
    return(
        <main>
            <CustomerLogin />
        </main>
    );
}