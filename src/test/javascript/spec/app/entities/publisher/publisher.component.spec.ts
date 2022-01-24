/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PublisherComponent from '@/entities/publisher/publisher.vue';
import PublisherClass from '@/entities/publisher/publisher.component';
import PublisherService from '@/entities/publisher/publisher.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Publisher Management Component', () => {
    let wrapper: Wrapper<PublisherClass>;
    let comp: PublisherClass;
    let publisherServiceStub: SinonStubbedInstance<PublisherService>;

    beforeEach(() => {
      publisherServiceStub = sinon.createStubInstance<PublisherService>(PublisherService);
      publisherServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PublisherClass>(PublisherComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          publisherService: () => publisherServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      publisherServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPublishers();
      await comp.$nextTick();

      // THEN
      expect(publisherServiceStub.retrieve.called).toBeTruthy();
      expect(comp.publishers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      publisherServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePublisher();
      await comp.$nextTick();

      // THEN
      expect(publisherServiceStub.delete.called).toBeTruthy();
      expect(publisherServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
