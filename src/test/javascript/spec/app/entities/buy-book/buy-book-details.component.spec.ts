/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import BuyBookDetailComponent from '@/entities/buy-book/buy-book-details.vue';
import BuyBookClass from '@/entities/buy-book/buy-book-details.component';
import BuyBookService from '@/entities/buy-book/buy-book.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('BuyBook Management Detail Component', () => {
    let wrapper: Wrapper<BuyBookClass>;
    let comp: BuyBookClass;
    let buyBookServiceStub: SinonStubbedInstance<BuyBookService>;

    beforeEach(() => {
      buyBookServiceStub = sinon.createStubInstance<BuyBookService>(BuyBookService);

      wrapper = shallowMount<BuyBookClass>(BuyBookDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { buyBookService: () => buyBookServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundBuyBook = { id: 123 };
        buyBookServiceStub.find.resolves(foundBuyBook);

        // WHEN
        comp.retrieveBuyBook(123);
        await comp.$nextTick();

        // THEN
        expect(comp.buyBook).toBe(foundBuyBook);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundBuyBook = { id: 123 };
        buyBookServiceStub.find.resolves(foundBuyBook);

        // WHEN
        comp.beforeRouteEnter({ params: { buyBookId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.buyBook).toBe(foundBuyBook);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
